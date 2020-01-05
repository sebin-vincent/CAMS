package com.litmus7.club.daoimpl;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.litmus7.club.daointerface.HrmsSheetDao;
import com.litmus7.club.dto.HrmsDto;
import com.litmus7.club.exception.HrmsSheetException;

public class HrmsSheetDaoImpl implements HrmsSheetDao {

	@Override
	public List<HrmsDto> getAbsenteesOfQuarter(LocalDate start_Date, LocalDate end_Date, String file_Name)
			throws HrmsSheetException, IOException {

		List<HrmsDto> hrms_Absentees = new ArrayList<HrmsDto>();
		HrmsDto hrms_Entry;

		File file = new File(file_Name);
		XSSFWorkbook workbook;

		try (FileInputStream in_Stream = new FileInputStream(file);) {

			workbook = new XSSFWorkbook(in_Stream);

		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("HrmsSheetDaoImpl:getAbsentees: " + "invalid file location");
		} catch (IOException e) {

			throw new IOException("HrmsSheetDaoImpl:getAbsentees: " + "IOException occured");

		}
		int number_Of_Sheets = workbook.getNumberOfSheets();
		if (number_Of_Sheets != 1) {
			throw new HrmsSheetException("The Hrms should contain only 1 sheet");
		}
		Sheet sheet = workbook.getSheetAt(0);
		if (sheet.getLastRowNum() < 1) {
			throw new HrmsSheetException("Given hrms sheet is empty..");
		}
		Iterator<Row> rows = sheet.rowIterator();
		Row current_Row = rows.next();
		if (!(current_Row.getCell(0).toString()).equalsIgnoreCase("Employee Id")) {
			throw new HrmsSheetException("Hrms sheet is not in valid format");
		}
		while (rows.hasNext()) {
			current_Row = rows.next();
			hrms_Entry= new HrmsDto();
			Optional<Cell> date = Optional.ofNullable(current_Row.getCell(1));
			if (date.isEmpty()) {
				throw new HrmsSheetException("Missing value at date column " + current_Row.getRowNum());
			}
			try {
				DateTimeFormatter formater1 = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
				LocalDate meeting_Date = LocalDate.parse(date.get().toString(), formater1);
				if (!(meeting_Date.isAfter(start_Date) & meeting_Date.isBefore(end_Date))) {
					continue;
				}
				hrms_Entry.setMeeting_Date(meeting_Date);
			} catch (DateTimeException e) {
				throw new HrmsSheetException("invalid date format at row : " + current_Row.getRowNum());
			}
			
			Optional<Cell> employee_Id_Cell = Optional.ofNullable(current_Row.getCell(0));
			if(employee_Id_Cell.isEmpty()) {
				throw new HrmsSheetException("Missing value at Employee Id Column :"+current_Row.getRowNum());
			}
			try {
				hrms_Entry.setEmployee_Id((int)(Double.parseDouble(employee_Id_Cell.get().toString())));
			} catch (NumberFormatException e) {
				throw new HrmsSheetException("Invalid Employee Id at row number : " + current_Row.getRowNum());
			}

			String temp_Status;
			boolean db_Status;
			
			Optional<Cell> approved_cell=Optional.ofNullable(current_Row.getCell(2));
			if(approved_cell.isEmpty()) {
				throw new HrmsSheetException("Missing value at Approved cell at row "+current_Row.getRowNum());
			}else {
				temp_Status=approved_cell.get().toString();
			}
			if (temp_Status.equalsIgnoreCase("yes")) {
				db_Status = true;
				hrms_Entry.setApproved(db_Status);
			} else if (temp_Status.equalsIgnoreCase("no")) {
				db_Status = false;
				hrms_Entry.setApproved(db_Status);
			} else {
				throw new HrmsSheetException(
						"invalid approves cell contents at row number :" + current_Row.getRowNum());
			}

			hrms_Absentees.add(hrms_Entry);

		}
		workbook.close();
		return hrms_Absentees;
	}
	
	


}
