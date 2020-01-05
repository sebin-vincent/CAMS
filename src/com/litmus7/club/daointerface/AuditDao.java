package com.litmus7.club.daointerface;

import java.io.IOException;
import java.sql.SQLException;

import com.litmus7.club.exception.FinancialYearException;
import com.litmus7.club.exception.HrmsSheetException;
import com.litmus7.club.exception.InvalidDataException;

/**
 * @author Litmus7_pc
 *Declares three method:- calculate actual method,claimed attendance,delete report from database
 */
public interface AuditDao {
	
	/**
	 * @param year
	 * @param quarter
	 * @param file_Name
	 * @throws FinancialYearException
	 * @throws SQLException
	 * @throws IOException
	 * @throws HrmsSheetException
	 * uploads actual attendance to database
	 * @throws InvalidDataException 
	 */
	public void getActualAttendance(int year,int quarter,String file_Name) throws FinancialYearException, SQLException, IOException, HrmsSheetException, InvalidDataException;
	/**
	 * @param year
	 * @param quarter
	 * @throws FinancialYearException
	 * @throws SQLException
	 * @throws IOException
	 * @throws HrmsSheetException
	 * uploads claimed attendace to database
	 */
	public void getClaimedAttendance(int year,int quarter) throws FinancialYearException, SQLException, IOException, HrmsSheetException;
	/**
	 * @param year
	 * @param quarter
	 * @throws SQLException
	 * @throws IOException
	 * @throws FinancialYearException
	 * delete reports from database
	 */
	public void deleteAudit(int year,int quarter) throws SQLException, IOException, FinancialYearException;
	
}
