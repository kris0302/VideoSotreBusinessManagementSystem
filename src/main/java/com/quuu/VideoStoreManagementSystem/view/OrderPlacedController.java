package com.quuu.VideoStoreManagementSystem.view;

import com.quuu.VideoStoreManagementSystem.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class OrderPlacedController {
    @FXML
    private Button go;

    private App app;

    @FXML
    private void handleGo(){
        app.LoadMyOrderPage();
    }

    public void setApp(App app){
		this.app = app;
	}
}
