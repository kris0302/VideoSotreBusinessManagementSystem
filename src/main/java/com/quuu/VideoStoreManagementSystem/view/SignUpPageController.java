package com.quuu.VideoStoreManagementSystem.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import com.quuu.VideoStoreManagementSystem.App;
import com.quuu.VideoStoreManagementSystem.util.SQLutil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SignUpPageController {
	@FXML
	private Button signin;
	@FXML
	private Button signup;
	@FXML
	private TextField email;
	@FXML
	private TextField password;
	@FXML
	private TextField fn,ln;
	@FXML
	private TextField address;
	@FXML
	private ChoiceBox<String> province;
	@FXML
	private ChoiceBox<String> type;
	@FXML
	private HBox nameblock1;
	@FXML
	private HBox nameblock2;
	@FXML
	private HBox addressblock;
	@FXML
	private HBox provinceblock;
	@FXML
	private Label prompt;
	
	private App app;
	private ObservableList<String> values;
	private ObservableList<String> values2;
	
	public SignUpPageController() {
		values=FXCollections.observableArrayList("NL","PE","ON","NS","NB","QC","MB","SK","AB","BC","YT","NT","NU");
		values2=FXCollections.observableArrayList("Customer","Admin","Operator","warehouseadmin");
	}
	
	@FXML
	private void handleSignin() {
		app.LoadWelcomePage();
	}
	
	@FXML
	private void handleSignUp() throws SQLException {
		app.RebuildConnection();
		String curEmail = email.getText();
		if (type.getValue().equals("Customer")) {
			if (email.getText().isEmpty() || password.getText().isEmpty() || fn.getText().isEmpty()
					|| ln.getText().isEmpty() || address.getText().isEmpty()) {
				prompt.setText("Please ensure no field is empty");
				return;
			}
			boolean test = Pattern.matches(".*@.*\\.com", email.getText());
			if (test == false) {
				prompt.setText("please enter a valid email");
			} else if (findDuplicateEmail("user") == true) {
				prompt.setText("This email is already registered!");
			} else {
				String[] values = { email.getText(), password.getText(), fn.getText(), ln.getText(), address.getText(),
						province.getValue(), "0", "0" };
				SQLutil.insertUser(app.getConnection(), values);
				app.LoadSignUpSuccessPage();
			}
		} else if (type.getValue().equals("Admin")) {
			if (email.getText().isEmpty() || password.getText().isEmpty()) {
				prompt.setText("Please ensure no field is empty");
				return;
			}
			if (curEmail.matches(".*@videostoreadmin.com")) {
				if (findDuplicateEmail("admin")) {
					prompt.setText("This admin email is already registered!");
				} else {
					SQLutil.insertAdminWarehouseadmin(app.getConnection(), email.getText(), password.getText(),"admin");
					app.LoadSignUpSuccessPage();
				}
			} else {
				prompt.setText("You must use company email to register");
			}
		} else if(type.getValue().equals("Operator")){
			if (email.getText().isEmpty() || password.getText().isEmpty()) {
				prompt.setText("Please ensure no field is empty");
				return;
			}
			if (curEmail.matches(".*@videostoreoperator.com")) {
				if (findDuplicateEmail("operator")) {
					prompt.setText("This operator email is already registered!");
				} else {
					SQLutil.insertOperator(app.getConnection(), email.getText(), password.getText());
					app.LoadSignUpSuccessPage();
				}
			} else {
				prompt.setText("You must use company email to register");
			}
		} else if(type.getValue().equals("warehouseadmin")){
			if (email.getText().isEmpty() || password.getText().isEmpty()) {
				prompt.setText("Please ensure no field is empty");
				return;
			}
			if (curEmail.matches(".*@videostorewarehouse.com")) {
				if (findDuplicateEmail("warehouseadmin")) {
					prompt.setText("This warehouse admin email is already registered!");
				} else {
					SQLutil.insertAdminWarehouseadmin(app.getConnection(), email.getText(), password.getText(),"warehouseadmin");
					app.LoadSignUpSuccessPage();
				}
			} else {
				prompt.setText("You must use company email to register");
			}
		}

	}

	private boolean findDuplicateEmail(String tablename) throws SQLException{
		app.RebuildConnection();
		ResultSet resu = SQLutil.select(app.getConnection(), "email",tablename, "email=\'"+email.getText()+"\'");
		if(!resu.isBeforeFirst()){
			return false;
		}else{
			return true;
		}
	}
	
	@FXML
	private void initialize() {
		province.setValue("ON");
		province.setItems(values);
		type.setValue("Customer");
		type.setItems(values2);
		type.setOnAction((event) -> {
			if(type.getValue().equals("Customer")){
				nameblock1.setVisible(true);
				nameblock2.setVisible(true);
				addressblock.setVisible(true);
				provinceblock.setVisible(true);
			}else{
				nameblock1.setVisible(false);
				nameblock2.setVisible(false);
				addressblock.setVisible(false);
				provinceblock.setVisible(false);
			}
		});
	}
	
	public void setApp(App app) {
		this.app = app;
	}
	
}
