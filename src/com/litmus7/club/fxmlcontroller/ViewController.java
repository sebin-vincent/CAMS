package com.litmus7.club.fxmlcontroller;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.litmus7.club.daoimpl.ClubDaoImpl;
import com.litmus7.club.daoimpl.RiverMemberDaoImpl;
import com.litmus7.club.dto.ClubDto;
import com.litmus7.club.dto.ReportDto;
import com.litmus7.club.exception.FinancialYearException;
import com.litmus7.club.exception.InvalidDataException;
import com.litmus7.club.utility.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class ViewController implements Initializable {
	@FXML
	private AnchorPane view_Report_Anchorpane;
	
	@FXML
	private Label warning;
	
	@FXML
	private ComboBox<Integer> year;
	
	@FXML
	private ComboBox<String> quarter = new ComboBox<String>();
	
	@FXML
	private TextField minimum;
	
	int minimum_attendance;
	
	int present_Year;
	
	List<ReportDto> boolokam_Reports =new ArrayList<ReportDto>();
	List<ReportDto> mayalokam_Reports =new ArrayList<ReportDto>();
	List<ReportDto> adholokam_Reports =new ArrayList<ReportDto>();
	List<ReportDto> devalokam_Reports =new ArrayList<ReportDto>();
	
	
	
	@FXML
	private void makeAudit(ActionEvent event) {
		Logger.logInfo("Move to make audit page..");
		try {
			AnchorPane pane=FXMLLoader.load(getClass().getResource("/com/litmus7/club/fxmlfile/main.fxml"));
			view_Report_Anchorpane.getChildren().setAll(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());

		}
	}
	
	
	
	@FXML
	private void logout(ActionEvent event) {
		Logger.logInfo("Loging out..");
		try {
			AnchorPane pane=FXMLLoader.load(getClass().getResource("/com/litmus7/club/fxmlfile/login.fxml"));
			view_Report_Anchorpane.getChildren().setAll(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());

		}
	}
	
	@FXML
	private void view(ActionEvent event) {
		try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("/com/litmus7/club/fxmlfile/report.fxml"));
			AnchorPane pane=loader.load();
			int input_year=year.getValue();
			int input_quarter;
			switch (quarter.getValue()) {
			
			case "1":
				input_quarter=1;
				break;
			case "2":
				input_quarter=2;
				break;
			case "3":
				input_quarter=3;
				break;
			case "4":
				input_quarter=4;
				break;
			default:
				input_quarter=5;
				break;
			}
			Logger.logInfo(input_year+","+input_quarter);
			String mini=minimum.getText();
			try {
				minimum_attendance=Integer.parseInt(mini);
				if(minimum_attendance>100) {
					throw new InvalidDataException("minimum attendance can't be greater than 100%");
				}
				RiverMemberDaoImpl objectCeo=new RiverMemberDaoImpl();
				ClubDaoImpl objClubDaoImpl=new ClubDaoImpl();
				List<ReportDto> reports=new ArrayList<ReportDto>();
				List<ClubDto> club_Details=new ArrayList<ClubDto>();
				ReportController controller=loader.getController();
				try {
					club_Details=objClubDaoImpl.getClubDetails(input_year, input_quarter);
					reports=objectCeo.viewReport(input_year, input_quarter,0);
				} catch (FinancialYearException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
				classifyClubs(reports,minimum_attendance);
				controller.setClubDetails(club_Details);
				controller.setBoolokamReports(boolokam_Reports);
				controller.setAdholokamReports(adholokam_Reports);
				controller.setMayalokamReports(mayalokam_Reports);
				controller.setDevalokamReports(devalokam_Reports);
				view_Report_Anchorpane.getChildren().setAll(pane);
			}catch (NumberFormatException e) {
				// TODO: handle exception
				warning.setText("minimum attendance is not in valid format");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				warning.setText(e.getMessage());
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			warning.setText("Something went wrong while rendering UI..");
		}
	}
	
	
	public void classifyClubs(List<ReportDto> reports,int minimum){
		
		for(ReportDto report:reports) {
			report.setMinimum(minimum);
			if(report.getClaimed_Attendance()>=minimum) {
				report.setMinimum_criteria("Criteria met");
			}else {
				report.setMinimum_criteria("Criteria not met");
			}
			switch(report.getClub_Id()) {
			case 1:
				devalokam_Reports.add(report);
				break;
			case 2:
				adholokam_Reports.add(report);
				break;
			case 3:
				mayalokam_Reports.add(report);
				break;
			case 4:
				boolokam_Reports.add(report);
				break;
			
			}
		}
		
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		present_Year=LocalDate.now().getYear();
		
		List<Integer> years=new ArrayList<Integer>();
		
		for(int this_Year=1999;this_Year<=present_Year;this_Year++) {
			years.add(this_Year);
		}
		
		year.getItems().addAll(years);
		year.getSelectionModel().selectFirst();
		quarter.getItems().addAll("1","2","3","4","All");
		quarter.getSelectionModel().selectFirst();
		
	}

}
