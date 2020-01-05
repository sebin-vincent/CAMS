package com.litmus7.club.dto;

/**
 * @author Litmus7_pc
 * Dto for transferring data from club database
 *
 */
public class AttendanceDto {
	private int employee_Id;
	private int meeting_Id;
	private char status;
	
	public int getEmployee_Id() {
		return employee_Id;
	}
	public void setEmployee_Id(int employee_Id) {
		this.employee_Id = employee_Id;
	}
	public int getMeeting_Id() {
		return meeting_Id;
	}
	public void setMeeting_Id(int meeting_Id) {
		this.meeting_Id = meeting_Id;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	
	

}
