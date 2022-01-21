package com.quuu.VideoStoreManagementSystem.view;

import com.quuu.VideoStoreManagementSystem.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SignUpSuccessPageController {
    @FXML
    private Button signin;
    private App app;
    @FXML
    private void handleSignin(){
        app.LoadWelcomePage();
    }

    public void setApp(App app) {
		this.app = app;
	}
}
