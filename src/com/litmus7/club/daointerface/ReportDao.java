package com.litmus7.club.daointerface;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.litmus7.club.dto.ReportDto;

public interface ReportDao {
	
	/**
	 * @param reports
	 * @throws SQLException
	 * @throws IOException
	 */
	public void uploadActualReport(List<ReportDto> reports) throws SQLException, IOException;
	/**
	 * @param reports
	 * @throws SQLException
	 * @throws IOException
	 * 
	 */
	public void uploadClaimedReport(List<ReportDto> reports) throws SQLException, IOException;
	/**
	 * @param startDate
	 * @param end_Date
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<ReportDto> getReport(LocalDate startDate,LocalDate end_Date) throws SQLException, IOException;

	public void deleteReport(LocalDate startDate,LocalDate endDate) throws SQLException, IOException;
}

