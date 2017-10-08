/**
 * @Author Shuo Zhang <shuo_zhang@gcitsolutions.com>
 * @Date Sep 28, 2017
 */
package com.gcit.libmgmtsys.dao;

import java.sql.*;
import java.util.*;

import com.gcit.libmgmtsys.entity.Book;
import com.gcit.libmgmtsys.entity.Borrower;

@SuppressWarnings({"unchecked", "rawtypes"})
public class BorrowerDAO extends BaseDAO{

	public BorrowerDAO(Connection conn) {
		super(conn);
	}
	
	//insert a new borrower
	public void addBorrower(Borrower borrower) throws SQLException {
		executeUpdate("INSERT INTO tbl_borrower (name, address, phone) VALUES(?, ?, ?)",
				new Object[] {borrower.getName(), borrower.getAddress(), borrower.getPhone()});
	}
	
	//insert a new borrower and return generated key
	public Integer addBorrowerWithID(Borrower borrower) throws SQLException {
		return executeUpdateWithID("INSERT INTO tbl_borrower (name, address, phone) VALUES(?, ?, ?)",
				new Object[] {borrower.getName(), borrower.getAddress(), borrower.getPhone()});
	}
	
	//update borrower information
	public void updateBorrower(Borrower borrower) throws SQLException {
		executeUpdate("UPDATE tbl_borrower SET name = ?, address = ?, phone = ? WHERE cardNo = ?", 
				new Object[] {borrower.getName(), borrower.getAddress(), borrower.getPhone()});
	}
	
	//delete a borrower
	public void deleteBorrower(Borrower borrower) throws SQLException {
		executeUpdate("DELETE FROM tbl_borrower WHERE cardNo = ?",
				new Object[] {borrower.getCardNo()});
	}
	
	public List<Borrower> readBorrowers(String borrowerName) throws SQLException {
		if (borrowerName != null && !borrowerName.isEmpty()) {
			borrowerName = "%" + borrowerName + "%";
			return executeQuery("SELECT * FROM tbl_author WHERE authorName LIKE ?",
					new Object[] {borrowerName});
		} else {
			return executeQuery("SELECT * FROM tbl_borrower", null);
		}
	}
	
	@Override
	protected List<Borrower> parseFirstLevelData(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setName(rs.getString("name"));
			borrower.setAddress(rs.getString("address"));
			borrower.setPhone(rs.getString("phone"));
			borrowers.add(borrower);
		}
		return borrowers;
	}

	@Override
	protected List<Borrower> parseData(ResultSet rs) throws SQLException {
		String sql = "SELECT b.bookId, b.title " + 
					 "FROM   tbl_book b, tbl_book_loans bl " + 
					 "WHERE  bl.cardNo = ? AND b.bookId = bl.bookId AND bl.dateIn IS NULL";
		List<Borrower> borrowers = new ArrayList<>();
		BookDAO bookDao = new BookDAO(conn);
		while (rs.next()) {
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setName(rs.getString("name"));
			borrower.setAddress(rs.getString("address"));
			borrower.setPhone(rs.getString("phone"));
			borrower.setBooksCheckedOut(bookDao.executeFirstLevelQuery(sql, new Object[] {borrower.getCardNo()}));
			borrowers.add(borrower);
		}
		return borrowers;
	}

	public Borrower readOneBorrowerFirstLevel(Integer cardNo) throws SQLException {
		List<Borrower> borrowers = executeFirstLevelQuery("SELECT * FROM tbl_borrower WHERE cardNo = ?", 
				new Object[] {cardNo});
		if (borrowers != null) {
			return borrowers.get(0);
		}
		return null;
	}

	
}
