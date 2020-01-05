package com.litmus7.club.fxmlcontroller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.litmus7.club.dto.ClubDto;
import com.litmus7.club.dto.ReportDto;
import com.litmus7.club.utility.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class ReportController implements Initializable {

	@FXML
	private AnchorPane report_Anchorpane;

	@FXML
	TableView<ReportDto> boolokam_Table = new TableView<ReportDto>();
	@FXML
	TableView<ReportDto> adholokam_Table = new TableView<ReportDto>();
	@FXML
	TableView<ReportDto> mayalokam_Table = new TableView<ReportDto>();
	@FXML
	TableView<ReportDto> devalokam_Table = new TableView<ReportDto>();
	@FXML
	TableView<ClubDto> club_Table = new TableView<ClubDto>();
	
	@FXML
	TableColumn<ClubDto, Integer> clubid;
	@FXML
	TableColumn<ClubDto, String> clubname;
	@FXML
	TableColumn<ClubDto, String> club_president;
	

	@FXML
	TableColumn<ReportDto, LocalDate> b_date;
	@FXML
	TableColumn<ReportDto, Integer> b_actual;
	@FXML
	TableColumn<ReportDto, Integer> b_claimed;
	@FXML
	TableColumn<ReportDto, Integer> b_minimum;
	@FXML
	TableColumn<ReportDto, String> b_criteria;

	@FXML
	TableColumn<ReportDto, LocalDate> a_date;
	@FXML
	TableColumn<ReportDto, Integer> a_actual;
	@FXML
	TableColumn<ReportDto, Integer> a_claimed;
	@FXML
	TableColumn<ReportDto, Integer> a_minimum;
	@FXML
	TableColumn<ReportDto, String> a_criteria;

	@FXML
	TableColumn<ReportDto, LocalDate> m_date;
	@FXML
	TableColumn<ReportDto, Integer> m_actual;
	@FXML
	TableColumn<ReportDto, Integer> m_claimed;
	@FXML
	TableColumn<ReportDto, Integer> m_minimum;
	@FXML
	TableColumn<ReportDto, String> m_criteria;

	@FXML
	TableColumn<ReportDto, LocalDate> d_date;
	@FXML
	TableColumn<ReportDto, Integer> d_actual;
	@FXML
	TableColumn<ReportDto, Integer> d_claimed;
	@FXML
	TableColumn<ReportDto, Integer> d_minimum;
	@FXML
	TableColumn<ReportDto, String> d_criteria;

	public void setBoolokamReports(List<ReportDto> list) {

		boolokam_Reports = FXCollections.observableArrayList(list);
		boolokam_Table.setItems(boolokam_Reports);
		
	}

	public void setDevalokamReports(List<ReportDto> list) {

		devalokam_Reports = FXCollections.observableArrayList(list);
		devalokam_Table.setItems(devalokam_Reports);
	}

	public void setAdholokamReports(List<ReportDto> list) {
		adholokam_Reports = FXCollections.observableArrayList(list);
		adholokam_Table.setItems(adholokam_Reports);
	}

	public void setMayalokamReports(List<ReportDto> list) {

		mayalokam_Reports = FXCollections.observableArrayList(list);
		mayalokam_Table.setItems(mayalokam_Reports);
	}
	
	public void setClubDetails(List<ClubDto> list) {

		club_details = FXCollections.observableArrayList(list);
		club_Table.setItems(club_details);
	}

	@FXML
	private void makeAudit(ActionEvent event) {
		Logger.logInfo("Move to make audit page..");
		try {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/litmus7/club/fxmlfile/main.fxml"));
			report_Anchorpane.getChildren().setAll(pane);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@FXML
	private void viewReport(ActionEvent event) {
		Logger.logInfo("move to view report page");
		try {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/litmus7/club/fxmlfile/view_report.fxml"));
			report_Anchorpane.getChildren().setAll(pane);
		} catch (IOException e) {
			System.out.println(e.getMessage());

		}
	}

	@FXML
	private void logout(ActionEvent event) {
		Logger.logInfo("Loging out..");
		try {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/litmus7/club/fxmlfile/login.fxml"));
			report_Anchorpane.getChildren().setAll(pane);
		} catch (IOException e) {
			System.out.println(e.getMessage());

		}
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		b_date.setCellValueFactory(new PropertyValueFactory<>("meeting_Date"));
		b_actual.setCellValueFactory(new PropertyValueFactory<>("actual_Attendance"));
		b_claimed.setCellValueFactory(new PropertyValueFactory<>("claimed_Attendance"));
		b_minimum.setCellValueFactory(new PropertyValueFactory<>("minimum"));
		b_criteria.setCellValueFactory(new PropertyValueFactory<>("minimum_criteria"));
		
		
		

		a_date.setCellValueFactory(new PropertyValueFactory<>("meeting_Date"));
		a_actual.setCellValueFactory(new PropertyValueFactory<>("actual_Attendance"));
		a_claimed.setCellValueFactory(new PropertyValueFactory<>("claimed_Attendance"));
		a_minimum.setCellValueFactory(new PropertyValueFactory<>("minimum"));
		a_criteria.setCellValueFactory(new PropertyValueFactory<>("minimum_criteria"));


		m_date.setCellValueFactory(new PropertyValueFactory<>("meeting_Date"));
		m_actual.setCellValueFactory(new PropertyValueFactory<>("actual_Attendance"));
		m_claimed.setCellValueFactory(new PropertyValueFactory<>("claimed_Attendance"));
		m_minimum.setCellValueFactory(new PropertyValueFactory<>("minimum"));
		m_criteria.setCellValueFactory(new PropertyValueFactory<>("minimum_criteria"));


		d_date.setCellValueFactory(new PropertyValueFactory<>("meeting_Date"));
		d_actual.setCellValueFactory(new PropertyValueFactory<>("actual_Attendance"));
		d_claimed.setCellValueFactory(new PropertyValueFactory<>("claimed_Attendance"));
		d_minimum.setCellValueFactory(new PropertyValueFactory<>("minimum"));
		d_criteria.setCellValueFactory(new PropertyValueFactory<>("minimum_criteria"));
		
		
		clubid.setCellValueFactory(new PropertyValueFactory<>("club_Id"));
		clubname.setCellValueFactory(new PropertyValueFactory<>("club_Name"));
		club_president.setCellValueFactory(new PropertyValueFactory<>("president_Name"));
		
		


	}

	private ObservableList<ReportDto> boolokam_Reports;
	private ObservableList<ReportDto> adholokam_Reports;
	private ObservableList<ReportDto> mayalokam_Reports;
	private ObservableList<ReportDto> devalokam_Reports;
	private ObservableList<ClubDto> club_details;

}
