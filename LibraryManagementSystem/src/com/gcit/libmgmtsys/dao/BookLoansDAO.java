/**
 * @Author Shuo Zhang <shuo_zhang@gcitsolutions.com>
 * @Date Sep 28, 2017
 */
package com.gcit.libmgmtsys.dao;

import java.sql.*;
import java.util.*;

import com.gcit.libmgmtsys.entity.BookLoans;

@SuppressWarnings({"unchecked", "rawtypes"})
public class BookLoansDAO extends BaseDAO{

	public BookLoansDAO(Connection conn) {
		super(conn);
	}
	
	//insert a new book loan
	public void addBookLoans(BookLoans bookLoans) throws SQLException {
		executeUpdate("INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) "
				+ "VALUES(?, ?, ?, NOW(), NOW() + INTERVAL 7 DAY)",
				new Object[] {bookLoans.getBookId(), bookLoans.getBranchId(), bookLoans.getCardNo()});
	}
	
	//insert a new book loan and return the generated ID
	public Integer addBookLoansWithID(BookLoans bookLoan) throws SQLException {
		return executeUpdateWithID("INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) "
				+ "VALUES(?, ?, ?, NOW(), NOW() + INTERVAL 7 DAY)",
				new Object[] {bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo()});
	}
	
	//update the check-in
	public void updateBookLoanCheckIn(BookLoans bookLoan) throws SQLException {
		executeUpdate("UPDATE tbl_book_loans SET dateIn = NOW() "
				+ "WHERE bookId = ? AND branchId = ? AND cardNo = ? AND dateOut = ?", 
				new Object[] {bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo(), bookLoan.getDateOut()});
	}
	
	//Override the due date of a book loan for 7 days.
	public void updateBookLoanOverride(BookLoans bookLoan) throws SQLException {
		executeUpdate("UPDATE tbl_book_loans SET dueDate = DATE_ADD(dueDate, INTERVAL 7 DAY) "
				+ "WHERE bookId = ? AND branchId = ? AND cardNo = ? AND dateOut = ?", 
				new Object[] {bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo(), bookLoan.getDateOut()});
	}
	
	public List<BookLoans> readBookLoans(String cardNo, String branchId) throws NumberFormatException, SQLException {
		if (cardNo != null && !cardNo.isEmpty() && branchId != null && !branchId.isEmpty()) {
			String sql = "SELECT bl.bookId, bl.branchId, bl.cardNo, b.title, lb.branchName, br.name, bl.dateOut, bl.dueDate, bl.dateIn " + 
					 	 "FROM tbl_book_loans bl, tbl_book b, tbl_library_branch lb, tbl_borrower br " + 
					 	 "WHERE bl.bookId = b.bookId AND bl.branchId = lb.branchId AND bl.cardNo = br.cardNo " +
					 	        "AND bl.branchId = ? AND bl.cardNo = ? AND bl.dateIn IS NULL";
			
			return executeFirstLevelQuery(sql, new Object[] {Integer.parseInt(branchId), Integer.parseInt(cardNo)});
		} else {
			String sql = "SELECT bl.bookId, bl.branchId, bl.cardNo, b.title, lb.branchName, br.name, bl.dateOut, bl.dueDate, bl.dateIn " + 
						 "FROM tbl_book_loans bl, tbl_book b, tbl_library_branch lb, tbl_borrower br " + 
						 "WHERE bl.bookId = b.bookId AND bl.branchId = lb.branchId AND bl.cardNo = br.cardNo";
			return executeFirstLevelQuery(sql, null);
		}
	}
	
//	public List<BookLoans> getBookLoansByCardNoAndBranchId(String cardNo, String branchId) {
//		String sql = "SELECT bl.bookId, bl.branchId, bl.cardNo, b.title, lb.branchName, br.name, bl.dateOut, bl.dueDate, bl.dateIn " + 
//				 	 "FROM tbl_book_loans bl, tbl_book b, tbl_library_branch lb, tbl_borrower br " + 
//				 	 "WHERE bl.bookId = b.bookId AND bl.branchId = ? AND bl.cardNo = ?";
//		List<BookLoans> bookLoans = new ArrayList<>();
//		return executeFirstLevelQuery();
//		
//	}
	
	//TO-DO: delete a book loan
	
	
	@Override
	protected List<BookLoans> parseFirstLevelData(ResultSet rs) throws SQLException {
		List<BookLoans> bookLoans = new ArrayList<>();
		while (rs.next()) {
			BookLoans bookLoan = new BookLoans();
			bookLoan.setBookId(rs.getInt("bookId"));
			bookLoan.setBranchId(rs.getInt("branchId"));
			bookLoan.setCardNo(rs.getInt("cardNo"));
			bookLoan.setBookTitle(rs.getString("title"));
			bookLoan.setBranchName(rs.getString("branchName"));
			bookLoan.setBorrowerName(rs.getString("name"));
			bookLoan.setDateOut(rs.getString("dateOut"));
			bookLoan.setDueDate(rs.getString("dueDate"));
			bookLoan.setDateIn(rs.getString("dateIn"));
			bookLoans.add(bookLoan);
		}
		return bookLoans;
	}

	@Override
	protected List<BookLoans> parseData(ResultSet rs) throws SQLException {
		List<BookLoans> bookLoans = new ArrayList<>();
		while (rs.next()) {
			BookLoans bookLoan = new BookLoans();
			bookLoan.setBookId(rs.getInt("bookId"));
			bookLoan.setBranchId(rs.getInt("branchId"));
			bookLoan.setCardNo(rs.getInt("cardNo"));
			bookLoan.setBookTitle("title");
			bookLoan.setBranchName("branchName");
			bookLoan.setBorrowerName("name");
			bookLoan.setDateOut(rs.getString("dateOut"));
			bookLoan.setDueDate(rs.getString("dueDate"));
			bookLoan.setDateIn(rs.getString("dateIn"));
			bookLoans.add(bookLoan);
		}
		return bookLoans;
	}
}
