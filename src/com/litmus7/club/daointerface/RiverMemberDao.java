package com.litmus7.club.daointerface;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.litmus7.club.dto.ReportDto;
import com.litmus7.club.exception.FinancialYearException;
import com.litmus7.club.exception.HrmsSheetException;
import com.litmus7.club.exception.InvalidDataException;

/**
 * @author Litmus7_pc
 *Declares three method
 *makeAudit:-To conduct Audit
 *viewReport:- To view report on an Audit
 *makeReAudit:-To make reaudit on an already happened audit
 */
public interface RiverMemberDao{
	
	/**
	 * @param year
	 * @param quarter
	 * @param file_Name
	 * @throws FinancialYearException
	 * @throws SQLException
	 * @throws IOException
	 * @throws HrmsSheetException
	 * @throws InvalidDataException
	 * make audit on particular quarter
	 */
	public void makeAudit(int year,int quarter,String file_Name) throws FinancialYearException, SQLException, IOException, HrmsSheetException, InvalidDataException;
	/**
	 * @param year
	 * @param quarter
	 * @param minimum_Attendance
	 * @return list of reports of all club
	 * @throws FinancialYearException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<ReportDto> viewReport(int year,int quarter,int minimum_Attendance) throws FinancialYearException, SQLException, IOException;
	/**
	 * @param year
	 * @param quarter
	 * @param file_Name
	 * @throws SQLException
	 * @throws IOException
	 * @throws FinancialYearException
	 * @throws HrmsSheetException
	 * Make a reaudit on already did audit
	 * @throws InvalidDataException 
	 * 
	 */
	public void makeReaudit(int year,int quarter,String file_Name) throws SQLException, IOException, FinancialYearException, HrmsSheetException, InvalidDataException;
}
