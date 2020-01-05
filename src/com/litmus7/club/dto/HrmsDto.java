package com.litmus7.club.dto;

import java.time.LocalDate;

/**
 * @author Litmus7_pc
 *Dto for transferring data from club database
 */
public class HrmsDto {
	
	private int employee_Id;
	private LocalDate meeting_Date;
	private boolean approved;
	
	
	public int getEmployee_Id() {
		return employee_Id;
	}
	
	public void setEmployee_Id(int employee_Id) {
		this.employee_Id = employee_Id;
	}
	
	public LocalDate getMeeting_Date() {
		return meeting_Date;
	}
	
	public void setMeeting_Date(LocalDate meeting_Date) {
		this.meeting_Date = meeting_Date;
	}
	
	public boolean getApproved() {
		return approved;
	}
	
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	

}
