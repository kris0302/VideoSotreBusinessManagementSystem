package com.quuu.VideoStoreManagementSystem.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import com.quuu.VideoStoreManagementSystem.App;
import com.quuu.VideoStoreManagementSystem.util.SQLutil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AccountManagementPageController {
    @FXML
    private Label email,name,rp,amount,prompt;
    @FXML
    private TextField password,address;
    @FXML
    private ChoiceBox<String> province;
    @FXML
    private Button update,pay,back;

    private App app;
    private ObservableList<String> values;

    public AccountManagementPageController(){
        values=FXCollections.observableArrayList("NL","PE","ON","NS","NB","QC","MB","SK","AB","BC","YT","NT","NU");
    }

    @FXML
    private void handleupdate() throws SQLException{
        if(update.getText().equals("Update")){
            password.setEditable(true);
            province.setDisable(false);
            province.setItems(values);
            address.setEditable(true);
            update.setText("Confirm");
        }else{
            app.RebuildConnection();
            password.setEditable(false);
            SQLutil.update(app.getConnection(), "user", "password=\'"+password.getText()+"\'", "email=\'"+email.getText()+"\'");
            province.setDisable(true);
            SQLutil.update(app.getConnection(), "user", "province=\'"+province.getValue()+"\'", "email=\'"+email.getText()+"\'");
            address.setEditable(false);
            SQLutil.update(app.getConnection(), "user", "address=\'"+address.getText()+"\'", "email=\'"+email.getText()+"\'");
            update.setText("Update");
        }
    }

    @FXML
    private void handleback(){
        app.LoadCustomerNavPage();
    }

    @FXML
    private void handlepay() throws SQLException{
    	/*app.RebuildConnection();
    	ResultSet resu = SQLutil.select(app.getConnection(), "*", "user", "email=\'"+app.getCurrentUser()+"\'");
    	resu.next();
    	float latefee = resu.getFloat("latefee");
    	ResultSet ordeResultSet = SQLutil.select(app.getConnection(), "*", "orders", "customer=\'"+app.getCurrentUser()+"\' AND status=\'Unpaid\'");
    	if(latefee==0 &&)*/
    	if(amount.getText().equals("$0.0")) {
    		prompt.setText("Nothing to Pay!");
    	}else {
    		 app.LoadPaymentPage();
    	}
       
    }

    @FXML
    private void init() throws SQLException{
    	app.RebuildConnection();
        String curemail = app.getCurrentUser();
        ResultSet resu = SQLutil.select(app.getConnection(), "*", "user", "email=\'"+curemail+"\'");
        resu.next();
        email.setText(resu.getString(1));
        password.setText(resu.getString(2));
        name.setText(resu.getString(3) +" "+ resu.getString(4));
        address.setText(resu.getString(5));
        province.setValue(resu.getString(6));
        province.setDisable(true);
        rp.setText(resu.getString(7));
        /* if(resu.getString(8).charAt(0)=='-'){
            SQLutil.update(app.getConnection(), "user", "set", "email=\'"+curemail+"\'");
        } */
        amount.setText("$"+resu.getString(8));
        if(resu.getFloat("latefee") != 0){
            DecimalFormat df = new DecimalFormat("0.00");
            amount.setText(amount.getText() + "+late fee$"+df.format(resu.getFloat("latefee")));
        }
    }

    public void setApp(App app) throws SQLException{
		this.app = app;
        init();
	}
}
