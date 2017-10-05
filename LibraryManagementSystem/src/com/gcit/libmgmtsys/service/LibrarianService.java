/**
 * @Author Shuo Zhang <shuo_zhang@gcitsolutions.com>
 * @Date Sep 28, 2017
 */
package com.gcit.libmgmtsys.service;

import java.sql.*;
import java.util.*;

import com.gcit.libmgmtsys.dao.BookCopiesDAO;
import com.gcit.libmgmtsys.dao.BookDAO;
import com.gcit.libmgmtsys.dao.LibraryBranchDAO;
import com.gcit.libmgmtsys.entity.Book;
import com.gcit.libmgmtsys.entity.BookCopies;
import com.gcit.libmgmtsys.entity.LibraryBranch;

public class LibrarianService {
	private Utilities util = new Utilities();
	
	public void updateBranchInfo(LibraryBranch libraryBranch) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			LibraryBranchDAO libraryBranchDao = new LibraryBranchDAO(conn);
			libraryBranchDao.updateLibraryBranch(libraryBranch);
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
	
	public void updateBookCopies(BookCopies bookCopies) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookCopiesDAO bookCopiesDao = new BookCopiesDAO(conn);
			bookCopiesDao.updateBookCopies(bookCopies);
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
	
	public List<LibraryBranch> readAllBranches() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			LibraryBranchDAO libraryBranchDao = new LibraryBranchDAO(conn);
			return libraryBranchDao.getLibraryBranches(null);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}
	
	public LibraryBranch readOneBranch(Integer branchId) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			LibraryBranchDAO branchDao = new LibraryBranchDAO(conn);
			return branchDao.getLibraryBranch(branchId);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}
	
	public LibraryBranch getBranchWithId(Integer branchId) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			LibraryBranchDAO libraryBranchDao = new LibraryBranchDAO(conn);
			return libraryBranchDao.getLibraryBranch(branchId);
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
	
	public void updateLibraryBranch(LibraryBranch branch) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			LibraryBranchDAO branchDao = new LibraryBranchDAO(conn);
			branchDao.updateLibraryBranch(branch);
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
	
}
