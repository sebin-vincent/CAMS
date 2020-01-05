package com.litmus7.club.fxmlcontroller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import com.litmus7.club.daoimpl.AuthenticationDaoImpl;
import com.litmus7.club.exception.InvalidDataException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * @author Litmus7_pc Controls the login and logout of user First ui page of the
 *         application
 */
public class LoginController {

	@FXML
	private AnchorPane rootPane;

	@FXML
	private TextField username;

	@FXML
	private TextField password;

	@FXML
	private Label warning;

	/**
	 * @param event Login button
	 */
	@FXML
	public void login(ActionEvent event) {
		String db_Password;
		String user = username.getText();
		String pass = password.getText();
		if ((!user.isBlank()) && (!pass.isBlank())) {
			try {
				AuthenticationDaoImpl object = new AuthenticationDaoImpl();
				db_Password = object.userLogin(user);
				if((pass).equals(db_Password)) {
					try {
						AnchorPane pane=FXMLLoader.load(getClass().getResource("/com/litmus7/club/fxmlfile/main.fxml"));
						rootPane.getChildren().setAll(pane);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("something went wrong with ui");
					}
				}else {
					
					warning.setText("Wrong password.Try again!");
				}
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			} catch (IOException e1) {
				System.out.println("something went with ui during authentication");
			} catch (InvalidDataException e1) {
				warning.setText("Username is not registered");
			}
		} else {
			warning.setText("please provide username and password !");
		}
	}

}
