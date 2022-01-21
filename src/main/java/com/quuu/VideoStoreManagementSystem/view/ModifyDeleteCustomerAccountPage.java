package com.quuu.VideoStoreManagementSystem.view;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.quuu.VideoStoreManagementSystem.App;
import com.quuu.VideoStoreManagementSystem.util.SQLutil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModifyDeleteCustomerAccountPage {
    @FXML
    private TextField email, password, fn, ln, address;
    @FXML
    private ChoiceBox<String> province;
    @FXML
    private Button update,back,retrieve,delete;
    @FXML
    private Label prompt;

    private App app;
    private ObservableList<String> values;

    public ModifyDeleteCustomerAccountPage(){
        values=FXCollections.observableArrayList("NL","PE","ON","NS","NB","QC","MB","SK","AB","BC","YT","NT","NU");
    }

    @FXML
    private void handleRetrieve() throws SQLException{
        app.RebuildConnection();
        ResultSet resu = SQLutil.select(app.getConnection(), "*", "user", "email=\'"+email.getText()+"\'");
        if(!resu.isBeforeFirst()){
            prompt.setText("User does not exist");
        }else{
            resu.next();
            email.setText(resu.getString("email"));
            password.setText(resu.getString("password"));
            fn.setText(resu.getString("firstname"));
            ln.setText(resu.getString("lastname"));
            address.setText(resu.getString("address"));
            province.setValue(resu.getString("province"));
        }
    }

    

    @FXML
    private void handleUpdate() throws SQLException{
        app.RebuildConnection();
        prompt.setText("");
        if(update.getText().equals("Update")){
            province.setItems(values);
            email.setDisable(true);
            password.setDisable(false);
            fn.setDisable(false);
            ln.setDisable(false);
            address.setDisable(false);
            province.setDisable(false);
            update.setText("Confirm");
        }else{
            SQLutil.update(app.getConnection(), "user", "password=\'"+password.getText()+"\'", "email=\'"+email.getText()+"\'");
            SQLutil.update(app.getConnection(), "user", "firstname=\'"+fn.getText()+"\'", "email=\'"+email.getText()+"\'");
            SQLutil.update(app.getConnection(), "user", "lastname=\'"+ln.getText()+"\'", "email=\'"+email.getText()+"\'");
            SQLutil.update(app.getConnection(), "user", "address=\'"+address.getText()+"\'", "email=\'"+email.getText()+"\'");
            SQLutil.update(app.getConnection(), "user", "province=\'"+province.getValue()+"\'", "email=\'"+email.getText()+"\'");
            update.setText("Update");
            email.setDisable(false);
            password.setDisable(true);
            fn.setDisable(true);
            ln.setDisable(true);
            address.setDisable(true);
            province.setDisable(true);
            /*email.clear();
            password.clear();
            fn.clear();
            ln.clear();
            address.clear();
            province.setValue(""); */
            prompt.setText("User info updated");
        }
        
    }

    @FXML
    private void handleBack(){
        app.LoadAdminNavPage();
    }

    @FXML
    private void handleDelete() throws SQLException{
    	app.RebuildConnection();
        SQLutil.delete(app.getConnection(), "user", "email=\'"+email.getText()+"\'");
        email.clear();
        password.clear();
        fn.clear();
        ln.clear();
        address.clear();
        province.setValue("");
        prompt.setText("User deleted");
    }

    public void setApp(App app){
		this.app = app;
	}
}
