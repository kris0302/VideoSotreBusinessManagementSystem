package com.quuu.VideoStoreManagementSystem.view;

import com.quuu.VideoStoreManagementSystem.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class OperatorOrderPlacedPageController {
    @FXML
    private Label id;
    @FXML
    private Button back;

    private App app;

    @FXML
    private void init(){
        id.setText("Order ID: "+app.getTempID());
    }

    @FXML
    private void handleBack(){
        app.LoadOperatorNavPage();
    }

    public void setApp(App app){
		this.app = app;
        init();
	}
}
