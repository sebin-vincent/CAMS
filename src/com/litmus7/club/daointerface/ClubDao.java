package com.litmus7.club.daointerface;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import com.litmus7.club.dto.ClubDto;
import com.litmus7.club.exception.FinancialYearException;

/**
 * @author Litmus7_pc
 *Defines getActiveclub,getMeetingDatesofClub,getEmployerMemberShip
 *
 *
 */
public interface ClubDao {
	/**
	 * @param end_Date
	 * @return list of clubs active till enddate
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Integer> getActiveClubs(LocalDate end_Date)throws SQLException, IOException;
	/**
	 * @param club_ID
	 * @param meeting_Date
	 * @return Stregth of club on date
	 * @throws SQLException
	 * @throws IOException
	 */
	public int getStrengthOfClub(int club_ID,LocalDate meeting_Date) throws SQLException, IOException;
	/**
	 * @param club_Id
	 * @param strat_Date
	 * @param end_Date
	 * @return meeting dates of between start and end date
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<LocalDate> getMeetingDatesOfClub(int club_Id,LocalDate strat_Date,LocalDate end_Date) throws SQLException, IOException;
	/**
	 * @param end_Date
	 * @return hashmap with key employee id and value clubid
	 * @throws SQLException
	 * @throws IOException
	 */
	public HashMap<Integer, Integer> getEmployeeMembership(LocalDate end_Date) throws SQLException, IOException;
	/**
	 * @param strat_Date
	 * @param end_Date
	 * @return list of meetings in particular meeting
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Integer> getAllMeetingIdsOfQuarter(LocalDate strat_Date,LocalDate end_Date) throws SQLException, IOException;
	/**
	 * @param club_ID
	 * @param meeting_Id
	 * @return strength of club on particular meeting id
	 * @throws SQLException
	 * @throws IOException
	 */
	public int getStrengthOfClub(int club_ID,int meeting_Id) throws SQLException, IOException;
	
	public List<ClubDto> getClubDetails(int year,int quarter) throws FinancialYearException,SQLException,IOException;

}
