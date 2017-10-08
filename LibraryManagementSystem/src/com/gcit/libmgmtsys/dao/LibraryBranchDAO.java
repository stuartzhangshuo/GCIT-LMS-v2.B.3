package com.gcit.libmgmtsys.dao;

import java.sql.*;
import java.util.*;

import com.gcit.libmgmtsys.entity.Borrower;
import com.gcit.libmgmtsys.entity.LibraryBranch;
import com.gcit.libmgmtsys.entity.Publisher;

@SuppressWarnings({"unchecked", "rawtypes"})
public class LibraryBranchDAO extends BaseDAO{
	public LibraryBranchDAO(Connection conn) {
		super(conn);
	}
	
	//insert a new library branch
	public void addLibraryBranch(LibraryBranch libraryBranch) throws SQLException {
		executeUpdate("INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES(?, ?)",
				new Object[] {libraryBranch.getBranchName(), libraryBranch.getBranchAddress()});
	}
	
	//insert a new library branch and return generated ID
	public Integer addLibraryBranchWithID(LibraryBranch libraryBranch) throws SQLException {
		return executeUpdateWithID("INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES(?, ?)",
				new Object[] {libraryBranch.getBranchName(), libraryBranch.getBranchAddress()});
	}
	
	//update the name of a library branch
	public void updateLibraryBranch(LibraryBranch libraryBranch) throws SQLException {
		executeUpdate("UPDATE tbl_library_branch SET branchName = ?, branchAddress = ? WHERE branchId = ?",
				new Object[] {libraryBranch.getBranchName(), libraryBranch.getBranchAddress(), libraryBranch.getBranchId()});
	}
	
	//delete an author from author table
	public void deleteLibraryBranch(LibraryBranch libraryBranch) throws SQLException {
		executeUpdate("DELETE FROM tbl_library_branch WHERE branchId = ?",
				new Object[] {libraryBranch.getBranchId()});
	}
	
	//execute query need to be tested
	public List<LibraryBranch> readLibraryBranches(String branchName, Integer pageNo) throws SQLException {
		setPageNo(pageNo);
		if (branchName != null && !branchName.isEmpty()) {
			branchName = "%" + branchName + "%";
			return executeQuery("SELECT * FROM tbl_library_branch WHERE branchName LIKE ?",
					new Object[] {branchName});
		} else {
			return executeQuery("SELECT * FROM tbl_library_branch", null);
		}
	}
	
	//Retrieve library info given library id
	public LibraryBranch readOneBranch(Integer branchId) throws SQLException {
		List<LibraryBranch> lb = executeQuery("SELECT * FROM tbl_library_branch WHERE branchId = ?",
				new Object[] {branchId});
		if (lb != null && !lb.isEmpty()) {
			return lb.get(0);
		}
		return null;
	}

	@Override
	protected List<LibraryBranch> parseFirstLevelData(ResultSet rs) throws SQLException {
		List<LibraryBranch> libraryBranches = new ArrayList<>();
		while (rs.next()) {
			LibraryBranch libraryBranch = new LibraryBranch();
			libraryBranch.setBranchId(rs.getInt("branchId"));
			libraryBranch.setBranchName(rs.getString("branchName"));
			libraryBranch.setBranchAddress(rs.getString("branchAddress"));
			libraryBranches.add(libraryBranch);
		}
		return libraryBranches;
	}
	
	//null pointer exception, Need to be tested
	@Override
	protected List<LibraryBranch> parseData(ResultSet rs) throws SQLException {
		String sql_loans  = "SELECT * FROM tbl_book_loans WHERE branchId = ? AND dateIn IS NULL";
		String sql_copies = "SELECT * FROM tbl_book_copies WHERE branchId = ?";
		List<LibraryBranch> libraryBranches = new ArrayList<>();
		BookLoansDAO 		bookLoans  		= new BookLoansDAO(conn);
		BookCopiesDAO   	bookCopies 		= new BookCopiesDAO(conn);
		while (rs.next()) {
			LibraryBranch libraryBranch = new LibraryBranch();
			libraryBranch.setBranchId(rs.getInt("branchId"));
			libraryBranch.setBranchName(rs.getString("branchName"));
			libraryBranch.setBranchAddress(rs.getString("branchAddress"));
			libraryBranch.setBookLoans(bookLoans.executeFirstLevelQuery(sql_loans, new Object[] {libraryBranch.getBranchId()}));
			libraryBranch.setBookCopies(bookCopies.executeFirstLevelQuery(sql_copies, new Object[] {libraryBranch.getBranchId()}));
			libraryBranches.add(libraryBranch);
		}
		return libraryBranches;
	}

	public LibraryBranch readOneLibrayBranchFirstLevel(Integer branchId) throws SQLException {
		List<LibraryBranch> libraryBranches = executeFirstLevelQuery("SELECT * FROM tbl_library_branch WHERE branchId = ?", 
				new Object[] {branchId});
		if (libraryBranches != null) {
			return libraryBranches.get(0);
		}
		return null;
	}

	public Integer getLibraryBranchesCount() throws SQLException {
		return getCount("SELECT COUNT(*) as COUNT FROM tbl_library_branch", null);
	}

	public List<LibraryBranch> checkBranchByName(String branchName) throws SQLException {
		List<LibraryBranch> libraryBranches = executeQuery("SELECT * FROM tbl_library_branch WHERE branchName = ?", 
				new Object[] {branchName});
		if (libraryBranches.size() > 0) {
			return libraryBranches;
		}
		return null;
	}
}
