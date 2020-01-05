package com.litmus7.club.dto;

import java.time.LocalDate;

/**
 * @author Litmus7_pc
 *Dto for transferring data of reports from and to database
 */
public class ReportDto {
	private int club_Id;
	private int meeting_Id;
	private int actual_Attendance;
	private int claimed_Attendance;
	private LocalDate meeting_Date;
	private int minimum=0;
	private String minimum_criteria;
	
	
	public String getMinimum_criteria() {
		return minimum_criteria;
	}

	public void setMinimum_criteria(String minimum_criteria) {
		this.minimum_criteria = minimum_criteria;
	}

	public int getMinimum() {
		return minimum;
	}

	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}

	public ReportDto() {
		// TODO Auto-generated constructor stub
	}
	
	public LocalDate getMeeting_Date() {
		return meeting_Date;
	}
	public void setMeeting_Date(LocalDate meeting_Date) {
		this.meeting_Date = meeting_Date;
	}
	public int getClub_Id() {
		return club_Id;
	}
	public void setClub_Id(int club_Id) {
		this.club_Id = club_Id;
	}
	public int getMeeting_Id() {
		return meeting_Id;
	}
	public void setMeeting_Id(int meeting_Id) {
		this.meeting_Id = meeting_Id;
	}
	public int getActual_Attendance() {
		return actual_Attendance;
	}
	public void setActual_Attendance(int actual_Attendance) {
		this.actual_Attendance = actual_Attendance;
	}
	public int getClaimed_Attendance() {
		return claimed_Attendance;
	}
	public void setClaimed_Attendance(int claimed_Attendance) {
		this.claimed_Attendance = claimed_Attendance;
	}

}
