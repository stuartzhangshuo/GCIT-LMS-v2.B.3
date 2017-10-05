/**
 * @Author Shuo Zhang <shuo_zhang@gcitsolutions.com>
 * @Date Sep 28, 2017
 */
package com.gcit.libmgmtsys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.libmgmtsys.dao.BookDAO;
import com.gcit.libmgmtsys.dao.BookLoansDAO;
import com.gcit.libmgmtsys.entity.Book;
import com.gcit.libmgmtsys.entity.BookLoans;

public class BorrowerService {
	private Utilities util = new Utilities();
	
	public void checkOutOneBook(BookLoans bookLoan) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookLoansDAO bookLoanDao = new BookLoansDAO(conn);
			bookLoanDao.addBookLoans(bookLoan);
			conn.commit();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public void checkInOneBook(BookLoans bookLoan) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookLoansDAO bookLoansDao = new BookLoansDAO(conn);
			bookLoansDao.updateBookLoanCheckIn(bookLoan);
			conn.commit();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
//	public List<Book> readBooksAvailable(String branchId) throws SQLException {
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			BookDAO bookDao = new BookDAO(conn);
//			return bookDao.getBooksWithBranchId(branchId);
//		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				conn.close();
//			}
//		}
//		return null;
//	}
	
	public List<BookLoans> readBookLoansByCardNoAndBranchId(String cardNo, String branchId) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookLoansDAO bookLoansDao = new BookLoansDAO(conn);
			return bookLoansDao.readBookLoans(cardNo, branchId);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

}
