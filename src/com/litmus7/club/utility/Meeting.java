package com.litmus7.club.utility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.club.jdbcconnector.DataBaseConnector;

public class Meeting {
	private static ResultSet result_Set = null;
	private static PreparedStatement statement = null;
	
	/**
	 * @param meeting_Date
	 * @return getMeetingId for a particular meeting date
	 * @throws SQLException
	 * @throws IOException
	 */
	public static int getMeetingId(LocalDate meeting_Date) throws SQLException, IOException {
		
		
		DataBaseConnector connector=new DataBaseConnector();
		Connection connection=connector.getDatabaseConnection();
		
		final String sql=QueryManager.getQuery("get_Meeting_ID");
		try {
			statement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new SQLException("Query preparation failed while fetching meeting id..");
		}
		statement.setDate(1, Date.valueOf(meeting_Date));
		
		try {
			result_Set = statement.executeQuery();
		} catch (SQLException e) {
			throw new SQLException("Query execution failed while fecthing meeting id..:");
		}
		
		result_Set.next();
		
		int meeting_id=result_Set.getInt("meeting_id");
		
		statement.close();
		result_Set.close();
		connector.closeDatabaseConnection(connection);
		
		
		return meeting_id;
	}
	
	/**
	 * @param meeting_ID
	 * @return list of absentees employee id on particular date
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Integer> getAllAbsentees(int meeting_ID) throws SQLException, IOException{
		
		List<Integer> absentees_Id=new ArrayList<Integer>();
		
		DataBaseConnector connector =new DataBaseConnector();
		Connection connection=connector.getDatabaseConnection();
		
		final String sql=QueryManager.getQuery("get_allAbsentees");
		try {
			statement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new SQLException("Query preparation failed while fetching absentees..");
		}
		statement.setInt(1, meeting_ID);
		
		try {
			result_Set = statement.executeQuery();
		} catch (SQLException e) {
			throw new SQLException("Query execution failed while fecthing absentees..:");
		}
		
		while (result_Set.next()) {

			absentees_Id.add(result_Set.getInt("employee_id"));
		}
		
		statement.close();
		result_Set.close();
		connector.closeDatabaseConnection(connection);
		return absentees_Id;
		
	}

}
