package com.quuu.VideoStoreManagementSystem.view;

import com.quuu.VideoStoreManagementSystem.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class OperatorNavPageController {
    @FXML
    private Button place,retrieve,logoff;

    private App app;

    @FXML
    private void handlelogoff(){
        app.LoadWelcomePage();
    }
    @FXML
    private void handlePlace(){
        app.LoadOperatorstep1Page();
    }
    @FXML
    private void handleRetrieve(){
        app.LoadAdminOrderPage();
    }

    public void setApp(App app) {
		this.app = app;
	}
}
