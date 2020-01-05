package com.litmus7.club.utility;
import java.time.LocalDate;

import com.litmus7.club.exception.FinancialYearException;

/**
 * @author Litmus7_pc
 *
 *methods-getStartDate,getEndDate
 */
public class FinancialYear {
	/**
	 * @param year
	 * @param quarter
	 * @return starting date of the given year and quarter
	 * @throws FinancialYearException
	 * 
	 */
	public static LocalDate getStartDate(int year,int quarter) throws FinancialYearException {
		LocalDate start_Date;
		String start="";
		switch(quarter) {
		case 1:
			start=year+"-03-31";
			break;
		case 2:
			start=year+"-06-30";
			break;
		case 3:
			start=year+"-09-30";
			break;
		case 4:
			start=year+"-12-31";
			break;
		case 5:
			start=year+"-03-31";
			break;
		default:
			throw new FinancialYearException("enter a valid year/quarter to get start date..");
		}
		start_Date=LocalDate.parse(start);
		
		return start_Date;
	}
	
	/**
	 * @param year
	 * @param quarter
	 * @return end date of given quarter and year
	 * @throws FinancialYearException
	 */
	public static LocalDate getEndDate(int year,int quarter) throws FinancialYearException {
		LocalDate end_Date;
		String end="";
		switch(quarter) {
		case 1:
			end=year+"-07-01";
			break;
		case 2:
			end=year+"-10-01";
			break;
		case 3:
			year+=1;
			end=year+"-01-01";
			break;
		case 4:
			year +=1;
			end=year+"-04-01";
			break;
		case 5:
			year +=1;
			end=year+"-04-01";
			break;
		default:
			throw new FinancialYearException("enter a valid financial year/quarter to get valid end date..");
		}
		end_Date=LocalDate.parse(end);
		return end_Date;
	}
	
	

}
