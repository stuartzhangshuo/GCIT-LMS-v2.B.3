/**
 * 
 */
package com.gcit.libmgmtsys.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.gcit.libmgmtsys.entity.BookCopies;

@SuppressWarnings({"unchecked", "rawtypes"})
public class BookAuthorDAO extends BaseDAO{
	public BookAuthorDAO(Connection conn) {
		super(conn);
	}
	//associate one book and one author and insert into database
	public void addBookAuthor(Integer bookId, Integer authorId) throws SQLException {
		executeUpdate("INSERT INTO tbl_book_authors VALUES(?, ?)",
				new Object[] {bookId, authorId});
	}

	@Override
	protected List parseFirstLevelData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List parseData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
