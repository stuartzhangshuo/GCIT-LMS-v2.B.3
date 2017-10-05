/**
 * @Book Shuo Zhang <shuo_zhang@gcitsolutions.com>
 * @Date Sep 28, 2017
 */
package com.gcit.libmgmtsys.dao;

import java.sql.*;
import java.util.*;

import com.gcit.libmgmtsys.entity.Author;
import com.gcit.libmgmtsys.entity.Book;
import com.gcit.libmgmtsys.entity.Publisher;

@SuppressWarnings({"unchecked", "rawtypes"})
public class BookDAO extends BaseDAO{
	
	public BookDAO(Connection conn) {
		super(conn);
	}
	//insert a new book into book table
	public void addBook(Book book) throws SQLException {
		executeUpdate("INSERT INTO tbl_book (bookName) VALUES(?)",
				new Object[] {book.getTitle()});
	}
	
	//insert a new book into book table and return generated ID
	public Integer addBookWithID(Book book) throws SQLException {
		return executeUpdateWithID("INSERT INTO tbl_book (bookName) VALUES(?)",
				new Object[] {book.getTitle()});
	}
	
	//update the name of a book
	public void updateBook(Book book) throws SQLException {
		executeUpdate("UPDATE tbl_book SET bookName = ? WHERE bookId = ?",
				new Object[] {book.getTitle(), book.getBookId()});
	}
	
	//update the publisher of a book
	public void updateBookPublisher(Publisher publisher) throws SQLException {
		executeUpdate("UPDATE tbl_book SET pubId = ?", new Object[] {publisher.getPublisherId()});
	}
	
	public void updateBookPublisherWithPubId(Integer bookId, Integer pubId) throws SQLException {
		executeUpdate("UPDATE tbl_book SET pubId = ? WHERE bookId = ?", new Object[] {pubId, bookId});
	}
	
	//delete an Book from book table
	public void deleteBook(Book book) throws SQLException {
		executeUpdate("DELETE FROM tbl_book WHERE bookId = ?",
				new Object[] {book.getBookId()});
	}
	//get all books or all books have a similar title to the input title
	public List<Book> getBooks(String bookTitle) throws SQLException {
		if (bookTitle != null && !bookTitle.isEmpty()) {
			bookTitle = "%" + bookTitle + "%";
			return executeQuery("SELECT * FROM tbl_book WHERE title LIKE ?",
					new Object[] {bookTitle});
		} else {
			return executeQuery("SELECT * FROM tbl_book", null);
		}
	}
	
//	public List<Book> getBooksWithBranchId(String branchId) throws NumberFormatException, SQLException {
//		AuthorDAO     authorDao     = new AuthorDAO(conn);
//		GenreDAO      genreDao      = new GenreDAO(conn);
//		PublisherDAO  publisherDao  = new PublisherDAO(conn);
//		//BookCopiesDAO bookCopiesDao = new BookCopiesDAO(conn);
//		
//		String sql = "SELECT * FROM tbl_book b, tbl_book_copies bc WHERE b.bookId = bc.bookId AND branchId = ?";
//		
//		String  sql_author = "SELECT * FROM tbl_author WHERE authorId IN " +
//							"(SELECT authorId FROM tbl_book_authors WHERE bookId = ?)";
//		
//		String  sql_publisher = "SELECT * FROM tbl_publisher WHERE publisherId IN " +
//								"(SELECT pubId FROM tbl_book WHERE bookId = ?)";
//		
//		String  sql_genre  = "SELECT * FROM tbl_genre WHERE genre_id IN " +
//							"(SELECT genre_id FROM tbl_book_genres WHERE bookId = ?)";
//		
//		
//		PreparedStatement pstmt = conn.prepareStatement(sql);
//		pstmt.setInt(1, Integer.parseInt(branchId));
//		ResultSet rs = pstmt.executeQuery();
//		List<Book> booksWithBranchId = new ArrayList<>();
//		while (rs.next()) {
//			Book book = new Book();
//			book.setBookId(rs.getInt(1));
//			book.setTitle(rs.getString(2));
//			book.setAuthors(authorDao.executeFirstLevelQuery(sql_author, new Object[] {book.getBookId()}));
//			book.setGenres(genreDao.executeFirstLevelQuery(sql_genre, new Object[] {book.getBookId()}));
//			book.setPublishers(publisherDao.executeFirstLevelQuery(sql_publisher, new Object[] {book.getBookId()}));
//			book.setNumOfCopies(rs.getInt("noOfCopies"));
//			booksWithBranchId.add(book);
//		}
//		return booksWithBranchId;
//	}
	
	@Override
	protected List<Book> parseData(ResultSet rs) throws SQLException {
		AuthorDAO   authorDao   = new AuthorDAO(conn);
		GenreDAO    genreDao    = new GenreDAO(conn);
		BorrowerDAO borrowerDao = new BorrowerDAO(conn);
		String  sql_author = "SELECT * FROM tbl_author WHERE authorId IN " +
					        "(SELECT authorId FROM tbl_book_authors WHERE bookId = ?)";
		
		String  sql_genre  = "SELECT * FROM tbl_genre WHERE genre_id IN " +
			                "(SELECT genre_id FROM tbl_book_genres WHERE bookId = ?)";
		
		String sql_borrowers = "SELECT * FROM tbl_borrower WHERE cardNo IN " +
                			  "(SELECT cardNo FROM tbl_book_loans WHERE bookId = ?)";
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			book.setPublisher(rs.getInt("pubId"));
			book.setAuthors(authorDao.executeFirstLevelQuery(sql_author, new Object[] {book.getBookId()}));
			book.setGenres(genreDao.executeFirstLevelQuery(sql_genre, new Object[] {book.getBookId()}));
			book.setBorrowers(borrowerDao.executeFirstLevelQuery(sql_borrowers, new Object[] {book.getBookId()}));
			books.add(book);
		}
		return books;
	}
	
	@Override
	protected List<Book> parseFirstLevelData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			book.setPublisher(rs.getInt("pubId"));
			books.add(book);
		}
		return books;
	}
	public Book readOneBook(Integer bookId) throws SQLException {
		List<Book> books = executeQuery("SELECT * FROM tbl_book WHERE bookId = ?", 
				new Object[] {bookId});
		if (books != null) {
			return books.get(0);
		}
		return null;
	}
}
