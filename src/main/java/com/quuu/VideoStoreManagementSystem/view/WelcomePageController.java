package com.quuu.VideoStoreManagementSystem.view;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.quuu.VideoStoreManagementSystem.App;
import com.quuu.VideoStoreManagementSystem.util.SQLutil;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class WelcomePageController {
	@FXML
	private Button signin_button;
	
	@FXML
	private Button signup_button; 
	
	@FXML
	private TextField signin_email;
	private String email;
	
	@FXML
	private PasswordField signin_password;
	private String password;
	
	@FXML
	private Label prompt;
	
	private App app;
	
	@FXML
	private void handleSignUp() {
		app.LoadSignUpPage();
	}
	
	@FXML 
	private void handleSignin() throws SQLException {
		app.RebuildConnection();
		String email = signin_email.getText();
		String password = signin_password.getText();
		if(email.isEmpty() || password.isEmpty()) {
			prompt.setText("Please don't test me with empty fields");
			return;
		}
		
		if(email.matches(".*@videostoreadmin.com")){
			ResultSet resu = SQLutil.select(app.getConnection(), "password", "admin", "email=\'"+email+"\'");
			if(!resu.isBeforeFirst()){
				prompt.setText("This admin email does not exist!");
			}else{
				resu.next();
				if(resu.getString("password").equals(password)){
					app.setCurrentUser(email);
					app.LoadAdminNavPage();
				}else{
					prompt.setText("Wrong password!");
				}
			}
		}else if(email.matches(".*@videostoreoperator.com")){
			ResultSet resu = SQLutil.select(app.getConnection(), "password", "operator", "email=\'"+email+"\'");
			if(!resu.isBeforeFirst()){
				prompt.setText("This admin email does not exist!");
			}else{
				resu.next();
				if(resu.getString("password").equals(password)){
					app.setCurrentUser(email);
					app.LoadOperatorNavPage();
				}else{
					prompt.setText("Wrong password!");
				}
			}
		}else if(email.matches(".*@videostorewarehouse.com")){
			ResultSet resu = SQLutil.select(app.getConnection(), "password", "warehouseadmin", "email=\'"+email+"\'");
			if(!resu.isBeforeFirst()){
				prompt.setText("This warehouse admin email does not exist!");
			}else{
				resu.next();
				if(resu.getString("password").equals(password)){
					app.setCurrentUser(email);
					app.LoadWarehousePage();
				}else{
					prompt.setText("Wrong password!");
				}
			}
		}else if(email.equals("root")){
			if(password.equals("root")){
				app.LoadRootPage();
			}else{
				prompt.setText("You are not root user");
			}
		}
		else{
			ResultSet resultSet = SQLutil.select(app.getConnection(), "password", "user", "email=\'"+email+"\'");
			if(resultSet.next() == true) {
				if(resultSet.getString(1).equals(password)) {
					app.setCurrentUser(email);
					app.LoadCustomerNavPage();
				}else {
					prompt.setText("Wrong password or email does not exist!");
				}
			}else {
				prompt.setText("Wrong password or email does not exist!");
			}
		}
		
	}
	
	
	public void setApp(App app) {
		this.app = app;
	}
}

