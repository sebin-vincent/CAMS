package com.litmus7.club.daointerface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.litmus7.club.dto.HrmsDto;
import com.litmus7.club.exception.HrmsSheetException;

public interface HrmsSheetDao {

	/**
	 * @param start_Date
	 * @param end_Date
	 * @param file_Name
	 * @return List of objects of absentees from hrms sheet
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws HrmsSheetException
	 */
	public List<HrmsDto> getAbsenteesOfQuarter(LocalDate start_Date, LocalDate end_Date, String file_Name)
			throws FileNotFoundException, IOException, HrmsSheetException;

	
	
	
}
