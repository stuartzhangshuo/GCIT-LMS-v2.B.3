/**
 * @Author Shuo Zhang <shuo_zhang@gcitsolutions.com>
 * @Date Sep 28, 2017
 */
package com.gcit.libmgmtsys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.libmgmtsys.dao.BookCopiesDAO;
import com.gcit.libmgmtsys.dao.BookDAO;
import com.gcit.libmgmtsys.dao.BookLoansDAO;
import com.gcit.libmgmtsys.dao.BorrowerDAO;
import com.gcit.libmgmtsys.dao.LibraryBranchDAO;
import com.gcit.libmgmtsys.entity.Book;
import com.gcit.libmgmtsys.entity.BookCopies;
import com.gcit.libmgmtsys.entity.BookLoans;
import com.gcit.libmgmtsys.entity.Borrower;
import com.gcit.libmgmtsys.entity.LibraryBranch;

public class BorrowerService {
	private Utilities util = new Utilities();
	
	public void checkOutOneBook(BookLoans bookLoan) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO 	  bookDao 	    = new BookDAO(conn);
			BookCopiesDAO bookCopiesDao = new BookCopiesDAO(conn);
			BookLoansDAO  bookLoanDao   = new BookLoansDAO(conn);
			//get book info & book count in each branch.
			Book book = bookDao.readOneBook(bookLoan.getBook().getBookId());	
			//update book copy info
			BookCopies bookCopy = new BookCopies();
			bookCopy.setBook(book);
			bookCopy.setLibraryBranch(bookLoan.getLibraryBranch());
			bookCopy.setNoOfCopies(book.getBranchCopies().get(bookLoan.getLibraryBranch().getBranchId()) - 1);
			//insert new book loan into tbl_book_loans, and update tbl_book_copies accordingly.
			bookLoanDao.addBookLoans(bookLoan);
			bookCopiesDao.updateBookCopies(bookCopy);
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
			
			BookDAO 	  bookDao 	    = new BookDAO(conn);
			BookCopiesDAO bookCopiesDao = new BookCopiesDAO(conn);
			BookLoansDAO  bookLoansDao   = new BookLoansDAO(conn);
			//get book info & book count in each branch.
			Book book = bookDao.readOneBook(bookLoan.getBook().getBookId());
			//update book copy info
			BookCopies bookCopy = new BookCopies();
			bookCopy.setBook(book);
			bookCopy.setLibraryBranch(bookLoan.getLibraryBranch());
			bookCopy.setNoOfCopies(book.getBranchCopies().get(bookLoan.getLibraryBranch().getBranchId()) + 1);
			//update book loan info in tbl_book_loans, and update tbl_book_copies accordingly.
			bookLoansDao.updateBookLoanCheckIn(bookLoan);
			bookCopiesDao.updateBookCopies(bookCopy);
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
	
	public List<Book> readBooks(String searchString, Integer pageNo) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO bookDao = new BookDAO(conn);
			return bookDao.readBooks(searchString, pageNo);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}
	
	public List<LibraryBranch> readLibraryBranches(String searchString, Integer pageNo) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			LibraryBranchDAO libraryBranchrDao = new LibraryBranchDAO(conn);
			return libraryBranchrDao.readLibraryBranches(searchString, pageNo);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
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
	
	public Borrower readOneBorrower(Integer cardNo) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BorrowerDAO borrowerDao = new BorrowerDAO(conn);
			return borrowerDao.readOneBorrower(cardNo);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}
	
	public List<BookLoans> readBookLoansByCardNoAndBranchId(Integer cardNo, Integer branchId) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookLoansDAO bookLoansDao = new BookLoansDAO(conn);
			return bookLoansDao.readBookLoansByCardNoAndBranchId(cardNo, branchId);
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
