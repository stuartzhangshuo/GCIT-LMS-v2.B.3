/**
 * @Author Shuo Zhang <shuo_zhang@gcitsolutions.com>
 * @Date Sep 27, 2017
 */
package com.gcit.libmgmtsys.dao;

import java.sql.*;
import java.util.*;

import com.gcit.libmgmtsys.entity.Author;
import com.gcit.libmgmtsys.entity.Book;

@SuppressWarnings({"unchecked", "rawtypes"})
public class AuthorDAO extends BaseDAO {
	public AuthorDAO(Connection conn) {
		super(conn);
	}
	//insert a new author into author table
	public void addAuthor(Author author) throws SQLException {
		executeUpdate("INSERT INTO tbl_author (authorName) VALUES(?)",
				new Object[] {author.getAuthorName()});
	}
	
	//insert a new author into author table and return generated ID
	public Integer addAuthorWithID(Author author) throws SQLException {
		return executeUpdateWithID("INSERT INTO tbl_author (authorName) VALUES(?)",
				new Object[] {author.getAuthorName()});
	}
	
	//associate author with all books in his book list
	public void addBookAuthor(Author author) throws SQLException {
		for (Book book : author.getBooks()) {
			executeUpdate("INSERT INTO tbl_book_authors VALUES(?, ?)", 
					new Object[] {book.getBookId(), author.getAuthorId()});
		}
	}
	
	//delete all books' author association with this author.
	public void deleteBookAuthor(Author author) throws SQLException {
		executeUpdate("DELETE FROM tbl_book_authors WHERE authorId = ?", new Object[] {author.getAuthorId()});
	}
	
	//update the name of an author
	public void updateAuthorName(Author author) throws SQLException {
		executeUpdate("UPDATE tbl_author SET authorName = ? WHERE authorId = ?",
				new Object[] {author.getAuthorName(), author.getAuthorId()});
	}
	
	//delete an author from author table
	public void deleteAuthor(Author author) throws SQLException {
		executeUpdate("DELETE FROM tbl_author WHERE authorId = ?",
				new Object[] {author.getAuthorId()});
	}
	
	public void updateBookAuthor(Author author) throws SQLException {
		for (Book book : author.getBooks()) {
			executeUpdate("INSERT INTO tbl_book_authors VALUES(?, ?)", new Object[] {book.getBookId(), author.getAuthorId()});
		}
	}
	
	//get all authors or all authors have a similar name to input name
	public List<Author> readAuthors(String authorName, Integer pageNo) throws SQLException {
		setPageNo(pageNo);
		if (authorName != null && !authorName.isEmpty()) {
			authorName = "%" + authorName + "%";
			return executeQuery("SELECT * FROM tbl_author WHERE authorName LIKE ?",
					new Object[] {authorName});
		} else {
			return executeQuery("SELECT * FROM tbl_author", null);
		}
	}
	
	public Author readOneAuthor(Integer authorId) throws SQLException {
		List<Author> authors = executeQuery("SELECT * FROM tbl_author WHERE authorId = ?", 
				new Object[] {authorId});
		if (authors != null) {
			return authors.get(0);
		}
		return null;
	}
	
	//return how many authors in the database
	public Integer getAuthorsCount() throws SQLException {
		return getCount("SELECT COUNT(*) as COUNT FROM tbl_author", null);
	}
	
	public List<Author> checkAuthorByName(String authorName) throws SQLException {
		List<Author> authors = executeQuery("SELECT * FROM tbl_author WHERE authorName = ?", 
				new Object[] {authorName});
		if (authors.size() > 0) {
			return authors;
		}
		return null;
	}
	
	@Override
	protected List<Author> parseFirstLevelData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			authors.add(author);
		}
		return authors;
	}
	
	@Override
	protected List<Author> parseData(ResultSet rs) throws SQLException {
		BookDAO bookDao = new BookDAO(conn);
		String  sql     = "SELECT * FROM tbl_book WHERE bookId IN " +
					     "(SELECT bookId FROM tbl_book_authors WHERE authorId = ?)";
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			author.setBooks(bookDao.executeFirstLevelQuery(sql, new Object[] {author.getAuthorId()}));
			authors.add(author);
		}
		return authors;
	}
}
