/**
 * 
 */
package com.gcit.libmgmtsys.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
public class BookGenreDAO extends BaseDAO{

	public BookGenreDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public void addBookGenre(Integer bookId, Integer genreId) throws SQLException {
		executeUpdate("INSERT INTO tbl_book_genres VALUES(?, ?)",
				new Object[] {genreId, bookId});
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
