package com.litmus7.club.daoimpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.club.daointerface.ReportDao;
import com.litmus7.club.dto.ReportDto;
import com.litmus7.club.jdbcconnector.DataBaseConnector;
import com.litmus7.club.utility.Logger;
import com.litmus7.club.utility.QueryManager;


public class ReportDaoImpl implements ReportDao {
	private PreparedStatement statement = null;
	

	@Override
	public void uploadActualReport(List<ReportDto> reports) throws SQLException, IOException {
		DataBaseConnector connector =new DataBaseConnector();
		Connection connection =connector.getDatabaseConnection();
		

		final String sql=QueryManager.getQuery("actual_Report_Upload");
		
		try {
			statement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new SQLException("Query preparation failed while uploading claimed actual attendace");
		}
		
		for(ReportDto report:reports) {
			
			statement.setInt(1, report.getClub_Id());
			statement.setInt(2, report.getMeeting_Id());
			statement.setInt(3,report.getActual_Attendance());
			
			try {
				statement.executeUpdate();
			} catch (SQLException e) {
				
				throw new SQLException(e.getMessage());
			}
			
		}
		connector.closeDatabaseConnection(connection);
		
	}

	@Override
	public void uploadClaimedReport(List<ReportDto> reports) throws SQLException, IOException {
		DataBaseConnector connector =new DataBaseConnector();
		Connection connection =connector.getDatabaseConnection();
		
		final String sql=QueryManager.getQuery("claimed_Report_Upload");
		
		try {
			statement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new SQLException("Query preparation failed while uploading claimed attendace");
		}
		
		for(ReportDto report:reports) {
			
			statement.setInt(1, report.getClaimed_Attendance());
			statement.setInt(2, report.getClub_Id());
			statement.setInt(3, report.getMeeting_Id());
			
			
			try {
				statement.executeUpdate();
			} catch (SQLException e) {
				
				throw new SQLException("update query failed while updating claimed attendace..");
			}
			
		}
		statement.close();
		connector.closeDatabaseConnection(connection);
		Logger.logInfo("Claimed attendance report uploaded successfully..");
		
	}
	
	@Override
	public List<ReportDto> getReport(LocalDate start_Date,LocalDate end_Date) throws SQLException, IOException {

		List<ReportDto> reports=new ArrayList<ReportDto>();
		ReportDto report;
		
		DataBaseConnector connector=new DataBaseConnector();
		Connection connection=connector.getDatabaseConnection();
		
		final String sql=QueryManager.getQuery("get_Report");
		
		try {
			statement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new SQLException("Query preparation failed while fecthing reports");
		}
		statement.setDate(1, Date.valueOf(start_Date));
		statement.setDate(2, Date.valueOf(end_Date));
		
		ResultSet result_Set2;
		try {
			result_Set2 = statement.executeQuery();
		} catch (SQLException e) {
			
			throw new SQLException("update query failed while fecthing reports");
		}
		
		while(result_Set2.next()) {
			report=new ReportDto();
			report.setClub_Id(result_Set2.getInt("club_id"));
			report.setMeeting_Date(result_Set2.getDate("meeting_date").toLocalDate());
			report.setActual_Attendance(result_Set2.getInt("actual_attendance"));
			report.setClaimed_Attendance(result_Set2.getInt("claimed_attendance"));
			reports.add(report);
		}
		
		statement.close();
		result_Set2.close();
		connector.closeDatabaseConnection(connection);

		
		return reports;
	}

	@Override
	public void deleteReport(LocalDate start_Date, LocalDate end_Date) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
		DataBaseConnector connector=new DataBaseConnector();
		Connection connection=connector.getDatabaseConnection();
		
		final String sql=QueryManager.getQuery("delete_Report");
		
		try {
			statement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new SQLException("Query preparation failed while deleting previous audit data");
		}
		statement.setDate(1, Date.valueOf(start_Date));
		statement.setDate(2, Date.valueOf(end_Date));
		
		try {
			statement.executeUpdate();
		} catch (SQLException e) {
			
			throw new SQLException("Query execution failed while deleting previous audit data");
		}
		statement.close();
		connector.closeDatabaseConnection(connection);
	}
		
		
}


