package com.litmus7.club.daoimpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.club.daointerface.RiverMemberDao;
import com.litmus7.club.dto.ReportDto;
import com.litmus7.club.exception.FinancialYearException;
import com.litmus7.club.exception.HrmsSheetException;
import com.litmus7.club.exception.InvalidDataException;
import com.litmus7.club.utility.FinancialYear;
import com.litmus7.club.utility.Logger;

/**
 * @author Litmus7_pc
 *defines two methods:-make Audit and view Report
 */
public class RiverMemberDaoImpl implements RiverMemberDao {


	@Override
	public void makeAudit(int year, int quarter, String file_Name)
			throws FinancialYearException, SQLException, IOException, HrmsSheetException, InvalidDataException {

		int file_year;
		int length=file_Name.length();  //length of file name
		if((length<10)) {
			throw new com.litmus7.club.exception.InvalidDataException("Please give the file in proper file name..");
		}
		try {
		file_year=Integer.parseInt(file_Name.substring(length-9, length-5));
		}catch (NumberFormatException e) {
			throw new InvalidDataException("Please give the file in proper file name..");
		}
		if(file_year!=year) {
			throw new InvalidDataException("The selected year and file_name is not matching..");
		}
		
		AuditDaoImpl objAuditDaoImpl = new AuditDaoImpl();
		objAuditDaoImpl.getActualAttendance(year, quarter, file_Name);
		objAuditDaoImpl.getClaimedAttendance(year, quarter);
		Logger.logInfo("Audit successful");
	}

	/**
	 *
	 */
	@Override
	public List<ReportDto> viewReport(int year, int quarter, int minimum_Attendance) throws FinancialYearException, SQLException, IOException {
		
		LocalDate start_Date=FinancialYear.getStartDate(year, quarter);
		LocalDate end_Date=FinancialYear.getEndDate(year, quarter);
		
		List<ReportDto> quarterReports=new ArrayList<ReportDto>();
		ReportDaoImpl objReportDaoImpl=new ReportDaoImpl();
		quarterReports=objReportDaoImpl.getReport(start_Date, end_Date);
		return quarterReports;

	}

	@Override
	public void makeReaudit(int year, int quarter, String file_Name) throws SQLException, IOException, FinancialYearException, HrmsSheetException, InvalidDataException {
		AuditDaoImpl objAuditDaoImpl=new AuditDaoImpl();
		objAuditDaoImpl.deleteAudit(year, quarter);
		Logger.logInfo("delete successful");
		objAuditDaoImpl.getActualAttendance(year, quarter, file_Name);
		objAuditDaoImpl.getClaimedAttendance(year, quarter);
		Logger.logInfo("ReAudit Successfull..");
		
	}

}
