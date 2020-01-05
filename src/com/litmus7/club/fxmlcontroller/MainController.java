package com.litmus7.club.fxmlcontroller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.litmus7.club.daoimpl.RiverMemberDaoImpl;
import com.litmus7.club.exception.FinancialYearException;
import com.litmus7.club.exception.HrmsSheetException;
import com.litmus7.club.exception.InvalidDataException;
import com.litmus7.club.utility.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * @author Litmus7_pc
 * 
 * Contains methods for audit and reAudit
 *
 */
/**
 * @author Litmus7_pc
 *
 */
/**
 * @author Litmus7_pc
 *
 */
public class MainController implements Initializable {
	public MainController() {
		// TODO Auto-generated constructor stub
		System.out.println("from constructor");
		
	}

	/**
	 * Variable keyword is used to check if audit has already conducted
	 */
	final String keyword = "Duplicate";

	RiverMemberDaoImpl objectCeo = new RiverMemberDaoImpl();

	@FXML
	private Button reAudit;

	@FXML
	private AnchorPane main_Anchorpane;

	@FXML
	private ComboBox<String> quarter = new ComboBox<String>();

	@FXML
	private ComboBox<Integer> year = new ComboBox<Integer>();

	@FXML
	FileChooser file_Chooser = new FileChooser();

	@FXML
	Label warning, choosefile;

	File file;
	
	int present_Year;

	/**
	 * @param event
	 * method to reAudit
	 */
	@FXML
	private void reAudit(ActionEvent event) {
		System.out.println("reAudit clicked");
		int input_Year;
		int input_Quarter;
		input_Year = year.getValue();
		
		switch (quarter.getValue()) {
		case "1":
			input_Quarter=1;
			break;
		case "2":
			input_Quarter=2;
			break;
		case "3":
			input_Quarter=3;
			break;
		case "4":
			input_Quarter=4;
			break;
		default:
			input_Quarter=5;
			break;
		}
		if (file != null) {
			String file_Name = file.getAbsolutePath();
			try {
				objectCeo.makeReaudit(input_Year, input_Quarter, file_Name);
				reAudit.setVisible(false);
				warning.setText("Audit successfull !");
			} catch (FinancialYearException e) {
				warning.setText(e.getMessage());
			} catch (SQLException e) {
				System.out.println(e.getMessage());

			} catch (IOException e) {
				System.out.println(e.getMessage());

			} catch (HrmsSheetException e) {
				System.out.println(e.getMessage());
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		} else {
			warning.setText("Please choose hrms sheet !");
		}

	}

	/**
	 * @param event for making audit
	 * 
	 * 
	 */
	@FXML
	private void makeAudit(ActionEvent event) {
		int input_Year;
		int input_Quarter;
		input_Year = year.getValue();
		
		switch (quarter.getValue()) {
		
		case "1":
			input_Quarter=1;
			break;
		case "2":
			input_Quarter=2;
			break;
		case "3":
			input_Quarter=3;
			break;
		case "4":
			input_Quarter=4;
			break;
		default:
			input_Quarter=5;
			break;
		}
		
		if (file != null) {
			String file_Name = file.getAbsolutePath();
			try {
				objectCeo.makeAudit(input_Year, input_Quarter, file_Name);
				warning.setText("Audit successfull !");
			} catch (FinancialYearException e) {
				warning.setText(e.getMessage());
			} catch (SQLException e) {
				if (e.getMessage().contains("Duplicate")) {
					reAudit.setVisible(true);
					warning.setText("This audit has already completed.You can now view the Report.");
				} else {
					System.out.println(e.getMessage());
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} catch (HrmsSheetException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				warning.setText(e.getMessage());
			}
		} else {
			warning.setText("Please choose hrms sheet !");
		}

	}

	/**
	 * @param event
	 * method to browse hrms sheet
	 */
	@FXML
	private void browseHrmsSheet(ActionEvent event) {
		Logger.logInfo("browse button clicked");
		file_Chooser.getExtensionFilters().addAll(new ExtensionFilter("Excel sheets", "*xlsx"));
		file_Chooser.setTitle("Select hrms sheet");
		Stage stage = (Stage) main_Anchorpane.getScene().getWindow();
		file = file_Chooser.showOpenDialog(stage);
		if (file != null) {
			String filename = file.getAbsolutePath();
			choosefile.setText(filename);
		} else {
			choosefile.setText("choose file");
			warning.setText("Please choose hrms sheet !");
		}

	}

	/**
	 * @param event
	 * method to move to view report page
	 * 
	 */
	@FXML
	private void viewReport(ActionEvent event) {
		Logger.logInfo("move to view report page");
		try {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/litmus7/club/fxmlfile/view_report.fxml"));
			main_Anchorpane.getChildren().setAll(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @param event
	 * method to logout user
	 */
	@FXML
	private void logout(ActionEvent event) {
		Logger.logInfo("Logging out");
		try {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/litmus7/club/fxmlfile/login.fxml"));
			main_Anchorpane.getChildren().setAll(pane);
		} catch (IOException e) {
			System.out.println(e.getMessage());

		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		present_Year=LocalDate.now().getYear();
		
		List<Integer> years=new ArrayList<Integer>();
		
		for(int this_Year=1999;this_Year<=present_Year;this_Year++) {
			years.add(this_Year);
		}
		
		quarter.getItems().addAll("1","2","3","4","All");
		quarter.getSelectionModel().selectFirst();
		year.getItems().addAll(years);
		year.getSelectionModel().selectFirst();
		
		reAudit.setVisible(false);
	}

}
