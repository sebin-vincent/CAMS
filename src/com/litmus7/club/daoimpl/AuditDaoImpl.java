package com.litmus7.club.daoimpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.litmus7.club.daointerface.AuditDao;
import com.litmus7.club.dto.HrmsDto;
import com.litmus7.club.dto.ReportDto;
import com.litmus7.club.exception.FinancialYearException;
import com.litmus7.club.exception.HrmsSheetException;
import com.litmus7.club.exception.InvalidDataException;
import com.litmus7.club.utility.FinancialYear;
import com.litmus7.club.utility.Logger;
import com.litmus7.club.utility.Meeting;

/**
 * @author Litmus7_pc
 *Defines getActualAttendance-calculate actual attendance from hrms sheet
 *
 *Defines getClaimedAttendance:-calculate claimed attendance from database.
 *
 */
public class AuditDaoImpl implements AuditDao {

	@Override
	public void getActualAttendance(int year, int quarter, String file_Name)
			throws FinancialYearException, SQLException, IOException, HrmsSheetException, InvalidDataException {
		LocalDate start_Date = FinancialYear.getStartDate(year, quarter);
		LocalDate end_Date = FinancialYear.getEndDate(year, quarter);
		if(!(start_Date.isBefore(LocalDate.now()) && end_Date.isBefore(LocalDate.now()))) {
			throw new FinancialYearException("can't make audit before end of quarter");
		}

		Logger.logInfo("start date: " + start_Date);
		Logger.logInfo("end date: " + end_Date);

		ClubDaoImpl objectClubDaoImpl = new ClubDaoImpl();

		List<Integer> active_Club = new ArrayList<Integer>();
		active_Club = objectClubDaoImpl.getActiveClubs(end_Date);

		HrmsSheetDaoImpl objectHrmsSheetDaoImpl = new HrmsSheetDaoImpl();
		List<HrmsDto> hrms_Entries = objectHrmsSheetDaoImpl.getAbsenteesOfQuarter(start_Date, end_Date, file_Name);
		HashMap<Integer, Integer> membeships = objectClubDaoImpl.getEmployeeMembership(end_Date);
		
		int strength;
		int absence;
		boolean all_Present;
		List<ReportDto> reports=new ArrayList<ReportDto>();
		ReportDto report;
			
		for (int club_Id : active_Club) {
			List<LocalDate> meeting_Dates = objectClubDaoImpl.getMeetingDatesOfClub(club_Id, start_Date, end_Date);
			for (LocalDate meeting_Date_from_DB : meeting_Dates) {
				report=new ReportDto();
				all_Present=true;
				strength = 0;
				absence = 0;
				int present=0;
				for (HrmsDto hrms_Entry : hrms_Entries) {
					if (club_Id== membeships.get(hrms_Entry.getEmployee_Id())
							&& hrms_Entry.getMeeting_Date().equals(meeting_Date_from_DB)
							&& hrms_Entry.getApproved() == false) {
						all_Present=false;
						absence += 1;
					}
				}
				if(all_Present==true) {
					report.setClub_Id(club_Id);
					report.setActual_Attendance(100);
					report.setMeeting_Id(Meeting.getMeetingId(meeting_Date_from_DB));
					report.setClaimed_Attendance(0);
					
				}else {
					strength=objectClubDaoImpl.getStrengthOfClub(club_Id, meeting_Date_from_DB);
					if(strength==0) {
						throw new InvalidDataException("The stregth of "+club_Id+" is 0");
					}
					present=strength-absence;
					report.setActual_Attendance((present*100/strength));
					report.setClub_Id(club_Id);
					report.setClaimed_Attendance(0);
					report.setMeeting_Id(Meeting.getMeetingId(meeting_Date_from_DB));
				}
				reports.add(report);
				
			}
			
		}
		ReportDaoImpl objReportDaoImpl=new ReportDaoImpl();
		objReportDaoImpl.uploadActualReport(reports);
		
	}

	@Override
	public void getClaimedAttendance(int year, int quarter)
			throws FinancialYearException, SQLException, IOException, HrmsSheetException {
		
		LocalDate start_Date = FinancialYear.getStartDate(year, quarter);
		LocalDate end_Date = FinancialYear.getEndDate(year, quarter);
		
		ClubDaoImpl objectClubDaoImpl = new ClubDaoImpl();
		
		List<Integer> active_club = new ArrayList<Integer>();
		active_club = objectClubDaoImpl.getActiveClubs(end_Date);
		List<Integer> meeting_Ids=objectClubDaoImpl.getAllMeetingIdsOfQuarter(start_Date, end_Date);
		HashMap<Integer, Integer> membeships = objectClubDaoImpl.getEmployeeMembership(end_Date);
		List<Integer> absent_Ids;
		HashMap<Integer, Integer> leaves;
		
		Meeting objMeeting=new Meeting();
		List<ReportDto> reports=new ArrayList<ReportDto>();
		ReportDto report;
		int club_id;
		int strength=0;
		int present;
		int claimed_Attendance;
		
		for(int meeting_Id:meeting_Ids) {
			leaves=new HashMap<Integer, Integer>();
			absent_Ids=objMeeting.getAllAbsentees(meeting_Id);
			for(int absent_Id:absent_Ids) {
				club_id=membeships.get(absent_Id);
				leaves.put(club_id, (leaves.getOrDefault(club_id, 0)+1));
			}
			for(int club:active_club) {
				report=new ReportDto();
				strength=objectClubDaoImpl.getStrengthOfClub(club, meeting_Id);
				present=strength-leaves.getOrDefault(club, 0);
				claimed_Attendance=(present*100/strength);
				report.setClub_Id(club);
				report.setMeeting_Id(meeting_Id);
				report.setClaimed_Attendance(claimed_Attendance);
				reports.add(report);
			}
			
		}
		
		ReportDaoImpl objReportDaoImpl=new ReportDaoImpl();
		objReportDaoImpl.uploadClaimedReport(reports);
		
		
		
		
		
		
		
	}

	@Override
	public void deleteAudit(int year, int quarter) throws SQLException, IOException, FinancialYearException {
		// TODO Auto-generated method stub
		
		LocalDate start_Date=FinancialYear.getStartDate(year, quarter);
		LocalDate end_Date=FinancialYear.getEndDate(year, quarter);
		ReportDaoImpl objReportDaoImpl=new ReportDaoImpl();
		objReportDaoImpl.deleteReport(start_Date, end_Date);
		
		
		
		
	}

}
