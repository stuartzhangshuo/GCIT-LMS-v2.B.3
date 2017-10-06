/**
 * @Author Shuo Zhang <shuo_zhang@gcitsolutions.com>
 * @Date Sep 28, 2017
 */
package com.gcit.libmgmtsys.dao;

import java.sql.*;
import java.util.*;

import com.gcit.libmgmtsys.entity.BookCopies;

@SuppressWarnings({"unchecked", "rawtypes"})
public class BookCopiesDAO extends BaseDAO{

	public BookCopiesDAO(Connection conn) {
		super(conn);
	}
	
	//insert a book copies association
	public void addBookCopies(BookCopies bookCopies) throws SQLException {
		executeUpdate("INSERT INTO tbl_book_copies (bookId, branchId, noOfCopies) VALUES(?, ?, ?)",
				new Object[] {bookCopies.getBookId(), bookCopies.getBranchId(), bookCopies.getNoOfCopies()});
	}
	
	//update a book copies association
	public void updateBookCopies(BookCopies bookCopies) throws SQLException {
		executeUpdate("UPDATE tbl_book_copies SET noOfCopies = ? WHERE bookId = ? AND branchId = ?",
				new Object[] {bookCopies.getNoOfCopies(), bookCopies.getBookId(), bookCopies.getBranchId()});
	}
	
	public List<BookCopies> getNoOfCopies(Integer bookId) throws SQLException {
		return executeQuery("SELECT bookId, branchId, sum(noOfCopies) as noOfCopies FROM tbl_book_copies WHERE bookId = ?", 
				new Object[] {bookId});
	}

	@Override
	protected List<BookCopies> parseFirstLevelData(ResultSet rs) throws SQLException {
		List<BookCopies> bookCopies = new ArrayList<>();
		while (rs.next()) {
			BookCopies bookCopy = new BookCopies();
			bookCopy.setBookId(rs.getInt("bookId"));
			bookCopy.setBranchId(rs.getInt("branchId"));
			bookCopy.setNoOfCopies(rs.getInt("noOfCopies"));
			bookCopies.add(bookCopy);
		}
		return bookCopies;
	}

	@Override
	protected List parseData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
