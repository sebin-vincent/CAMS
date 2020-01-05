package com.litmus7.club.daoimpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.litmus7.club.daointerface.ClubDao;
import com.litmus7.club.dto.ClubDto;
import com.litmus7.club.exception.FinancialYearException;
import com.litmus7.club.jdbcconnector.DataBaseConnector;
import com.litmus7.club.utility.FinancialYear;
import com.litmus7.club.utility.QueryManager;

public class ClubDaoImpl implements ClubDao {
	private ResultSet result_Set = null;
	private PreparedStatement statement = null;

	
	@Override
	public List<Integer> getActiveClubs(LocalDate end_Date) throws SQLException, IOException {

		List<Integer> active_clubs = new ArrayList<Integer>();

		DataBaseConnector connector = new DataBaseConnector();
		Connection connection = connector.getDatabaseConnection();
		final String sql =QueryManager.getQuery("get_Active_Club");

		try {
			statement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new SQLException("Query preparation failed while fetching active clubs..");
		}
		statement.setString(1, end_Date.toString());

		try {
			result_Set = statement.executeQuery();
		} catch (SQLException e) {
			throw new SQLException("Query execution failed at active clubs:");
		}

		while (result_Set.next()) {

			active_clubs.add(result_Set.getInt("club_id"));

		}
		statement.close();
		result_Set.close();
		connector.closeDatabaseConnection(connection);

		return active_clubs;
	}

	@Override
	public int getStrengthOfClub(int club_Id,LocalDate meeting_Date) throws SQLException, IOException {
		DataBaseConnector connector=new DataBaseConnector();
		Connection connection=connector.getDatabaseConnection();
		final String sql = QueryManager.getQuery("get_Stregth_With_Date");
		
		try {
			statement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new SQLException("Query preparation failed while fetching strength of club..");
		}
		statement.setInt(1, club_Id);
		statement.setDate(2, Date.valueOf(meeting_Date));
		statement.setDate(3, Date.valueOf(meeting_Date));
		
		try {
			result_Set = statement.executeQuery();
		} catch (SQLException e) {
			throw new SQLException("Query execution failed while fecthing strength of club..:");
		}
		result_Set.next();
		int strength=result_Set.getInt(1);
		
		statement.close();
		result_Set.close();
		connector.closeDatabaseConnection(connection);
		
		return strength;
	}

	@Override
	public List<LocalDate> getMeetingDatesOfClub(int club_Id, LocalDate start_Date, LocalDate end_Date)
			throws SQLException, IOException {
		List<LocalDate> meeting_Dates = new ArrayList<LocalDate>();
		DataBaseConnector connector = new DataBaseConnector();
		Connection connection = connector.getDatabaseConnection();
		
		final String sql =QueryManager.getQuery("get_Meetingsof_Date");

		try {
			statement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new SQLException("Query preparation failed while fetching meeting dates of club..");
		}
		statement.setInt(1, club_Id);
		statement.setString(2, start_Date.toString());
		statement.setString(3, end_Date.toString());

		try {
			result_Set = statement.executeQuery();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException("Query execution failed while  fecthing meeting dates of clubs:");
		}
		while (result_Set.next()) {

			meeting_Dates.add(result_Set.getDate("meeting_date").toLocalDate());

		}
		statement.close();
		result_Set.close();
		connector.closeDatabaseConnection(connection);

		return meeting_Dates;
	}

	@Override
	public HashMap<Integer, Integer> getEmployeeMembership(LocalDate end_Date) throws SQLException, IOException {
		HashMap<Integer, Integer> membership=new HashMap<Integer, Integer>();
		DataBaseConnector connector=new DataBaseConnector();
		Connection connection=connector.getDatabaseConnection();
		final String sql=QueryManager.getQuery("get_ClubMembership");
		try {
			statement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new SQLException("Query preparation failed while getting membership of employee ids...");
		}
		
		statement.setDate(1, Date.valueOf(end_Date));
		
		try {
			result_Set = statement.executeQuery();
		} catch (SQLException e) {
			throw new SQLException("Query execution failed while fetching membership of employees:");
		}
		
		while (result_Set.next()) {

			membership.put(result_Set.getInt("employee_id"), result_Set.getInt("club_id"));
		}
		
		statement.close();
		result_Set.close();
		connector.closeDatabaseConnection(connection);
		
		return membership;
	}

	@Override
	public List<Integer> getAllMeetingIdsOfQuarter(LocalDate start_Date, LocalDate end_Date) throws SQLException, IOException {
		List<Integer> meeting_Ids=new ArrayList<Integer>();
		
		DataBaseConnector connector=new DataBaseConnector();
		Connection connection=connector.getDatabaseConnection();
		
		final String sql=QueryManager.getQuery("get_MeetingIds_Of_Quarter");
		
		try {
			statement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new SQLException("Query preparation failed while getting meeting ids of quarter...");
		}
		statement.setDate(1, Date.valueOf(start_Date));
		statement.setDate(2, Date.valueOf(end_Date));
		
		try {
			result_Set = statement.executeQuery();
		} catch (SQLException e) {
			throw new SQLException("Query execution failed while fetching meeting ids of quarter:");
		}
		
		while (result_Set.next()) {

			meeting_Ids.add(result_Set.getInt("meeting_id"));
		}
		
		statement.close();
		result_Set.close();
		connector.closeDatabaseConnection(connection);
		
		
		return meeting_Ids;
	}

	@Override
	public int getStrengthOfClub(int club_ID, int meeting_Id) throws SQLException, IOException {
		DataBaseConnector connector=new DataBaseConnector();
		Connection connection=connector.getDatabaseConnection();
		final String sql =QueryManager.getQuery("get_Stregth_With_Id");

		

		try {
			statement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new SQLException("Query preparation failed while fetching strength of club..");
		}
		statement.setInt(1, meeting_Id);
		statement.setInt(2, club_ID);
		
		try {
			result_Set = statement.executeQuery();
		} catch (SQLException e) {
			throw new SQLException("Query execution failed while fecthing strength of club..:");
		}
		result_Set.next();
		int strength=result_Set.getInt(1);
		
		statement.close();
		result_Set.close();
		connector.closeDatabaseConnection(connection);
		
		return strength;
	}

	@Override
	public List<ClubDto> getClubDetails(int year, int quarter)
			throws FinancialYearException, SQLException, IOException {
		
		
		List<ClubDto> list_Of_Club_Details=new ArrayList<ClubDto>();
		LocalDate end_Date=FinancialYear.getEndDate(year, quarter);
		
		DataBaseConnector connector = new DataBaseConnector();
		Connection connection = connector.getDatabaseConnection();
		final String sql = QueryManager.getQuery("get_Club_Details");

		try {
			statement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new SQLException("Query preparation failed while fetching clubs details..");
		}
		statement.setString(1, end_Date.toString());

		try {
			result_Set = statement.executeQuery();
		} catch (SQLException e) {
//			System.out.println(e.getMessage());
			throw new SQLException("Query execution failed while clubs details..");
		}
		ClubDto club_Detail;

		while (result_Set.next()) {
			club_Detail=new ClubDto();
			club_Detail.setClub_Id(result_Set.getInt("club_id"));
			club_Detail.setClub_Name(result_Set.getString("club_name"));
			club_Detail.setPresident_Name(result_Set.getString("president"));
			list_Of_Club_Details.add(club_Detail);
		}
		statement.close();
		result_Set.close();
		connector.closeDatabaseConnection(connection);
		
		
		
		return list_Of_Club_Details;
	}

}
