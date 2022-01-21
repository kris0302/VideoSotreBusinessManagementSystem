package com.quuu.VideoStoreManagementSystem.view;

import java.sql.SQLException;

import com.quuu.VideoStoreManagementSystem.App;
import com.quuu.VideoStoreManagementSystem.util.SQLutil;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminNavPageController {
    @FXML
    private Button addmovie,modifyaccount,modifymovie,order,logoff;

    private App app;

    @FXML
    private void handleLogoff(){
        app.LoadWelcomePage();
    }
    @FXML
    private void handleAddMovie(){
        app.LoadAddMoviePage();
    }
    @FXML
    private void handleModifyAccount(){
        app.LoadModifyDeleteCustomerAccountPage();
    }
    @FXML
    private void handleLoadOrder(){
        app.LoadAdminOrderPage();
    }
    @FXML
    private void handleModifyMovie(){
        app.LoadMovieModifyPage();
    }

    @FXML
    private void handleDeleteAccount() throws SQLException{
        app.RebuildConnection();
        SQLutil.delete(app.getConnection(), "admin", "email=\'"+app.getCurrentUser()+"\'");
        app.LoadWelcomePage();
    }

    public void setApp(App app){
		this.app = app;
	}
}
