/**
 * @Genre Shuo Zhang <shuo_zhang@gcitsolutions.com>
 * @Date Sep 28, 2017
 */
package com.gcit.libmgmtsys.dao;

import java.sql.*;
import java.util.*;

import com.gcit.libmgmtsys.entity.Genre;
import com.gcit.libmgmtsys.entity.Author;
import com.gcit.libmgmtsys.entity.Book;

@SuppressWarnings({"unchecked", "rawtypes"})
public class GenreDAO extends BaseDAO{
	public GenreDAO(Connection conn) {
		super(conn);
	}
	
	public void addGenre(Genre genre) throws SQLException {
		executeUpdate("INSERT INTO tbl_genre (genre_name) VALUES(?)",
				new Object[] {genre.getGenreName()});
	}
	
	//insert a new genre into genre table and return generated ID
	public Integer addGenreWithID(Genre genre) throws SQLException {
		return executeUpdateWithID("INSERT INTO tbl_genre (genre_name) VALUES(?)",
				new Object[] {genre.getGenreName()});
	}
	
	//associate genre with all books in his book list
	public void addBookGenre(Genre genre) throws SQLException {
		for (Book book : genre.getBooks()) {
			executeUpdate("INSERT INTO tbl_book_genres VALUES(?, ?)", 
					new Object[] {genre.getGenreId(), book.getBookId()});
		}
	}
	
	//update the name of an genre
	public void updateGenreName(Genre genre) throws SQLException {
		executeUpdate("UPDATE tbl_genre SET genre_name = ? WHERE genre_id = ?",
				new Object[] {genre.getGenreName(), genre.getGenreId()});
	}
	
	//delete an genre from genre table
	public void deleteGenre(Genre genre) throws SQLException {
		executeUpdate("DELETE FROM tbl_genre WHERE genre_id = ?",
				new Object[] {genre.getGenreId()});
	}
	
	//get all genres or all genres have a similar name to input name
	public List<Genre> getGenres(String genreName) throws SQLException {
		if (genreName != null && !genreName.isEmpty()) {
			genreName = "%" + genreName + "%";
			return executeQuery("SELECT * FROM tbl_genre WHERE genre_name LIKE ?",
					new Object[] {genreName});
		} else {
			return executeQuery("SELECT * FROM tbl_genre", null);
		}
	}
	
	//get all genres or all genres have a similar name to input name
	public List<Genre> readGenres(String genreName, Integer pageNo) throws SQLException {
		setPageNo(pageNo);
		if (genreName != null && !genreName.isEmpty()) {
			genreName = "%" + genreName + "%";
			return executeQuery("SELECT * FROM tbl_genre WHERE genre_name LIKE ?",
					new Object[] {genreName});
		} else {
			return executeQuery("SELECT * FROM tbl_genre", null);
		}
	}
	
	public Integer getGenresCount() throws SQLException {
		return getCount("SELECT COUNT(*) as COUNT FROM tbl_genre", null);
	}
	
	public Genre readOneGenre(Integer genreId) throws SQLException {
		List<Genre> genres = executeQuery("SELECT * FROM tbl_genre WHERE genre_id = ?", 
				new Object[] {genreId});
		if (genres != null) {
			return genres.get(0);
		}
		return null;
	}
	
	@Override
	protected List<Genre> parseFirstLevelData(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<>();
		while (rs.next()) {
			Genre genre = new Genre();
			genre.setGenreId(rs.getInt("genre_id"));
			genre.setGenreName(rs.getString("genre_name"));
			genres.add(genre);
		}
		return genres;
	}

	@Override
	protected List<Genre> parseData(ResultSet rs) throws SQLException {
		BookDAO bookDao = new BookDAO(conn);
		String  sql     = "SELECT * FROM tbl_book WHERE bookId IN " +
					     "(SELECT bookId FROM tbl_book_genres WHERE genre_id = ?)";
		List<Genre> genres = new ArrayList<>();
		while (rs.next()) {
			Genre genre = new Genre();
			genre.setGenreId(rs.getInt("genre_id"));
			genre.setGenreName(rs.getString("genre_name"));
			genre.setBooks(bookDao.executeFirstLevelQuery(sql, new Object[] {genre.getGenreId()}));
			genres.add(genre);
		}
		return genres;
	}

	public List<Genre> checkGenreByName(String genreName) throws SQLException {
		List<Genre> genres = executeQuery("SELECT * FROM tbl_genre WHERE genre_name = ?", 
				new Object[] {genreName});
		if (genres.size() > 0) {
			return genres;
		}
		return null;
	}

	public void deleteBookGenre(Genre genre) throws SQLException {
		executeUpdate("DELETE FROM tbl_book_genres WHERE genre_id = ?", new Object[] {genre.getGenreId()});
	}

	public void updateBookGenre(Genre genre) throws SQLException {
		for (Book book : genre.getBooks()) {
			executeUpdate("INSERT INTO tbl_book_genres VALUES(?, ?)", new Object[] {genre.getGenreId(), book.getBookId()});
		}
	}
}
