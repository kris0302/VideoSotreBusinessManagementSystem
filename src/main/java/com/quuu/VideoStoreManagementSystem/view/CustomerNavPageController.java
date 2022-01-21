package com.quuu.VideoStoreManagementSystem.view;

import com.quuu.VideoStoreManagementSystem.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CustomerNavPageController {
    @FXML
    private Button find;
    @FXML
    private Button order;
    @FXML
    private Button account;
    @FXML
    private Button logff;
    @FXML
    private Button orders;

    private App app;

    @FXML
    private void handlefind(){
        app.LoadFindMoviePage();
    }
    /*@FXML
    private void handleorder(){
        app.loadOrderOverviewPage();
    }*/
    @FXML
    private void handleaccount(){
        app.LoadAccountManagementNavPage();
    }
    @FXML
    private void handlelogoff(){
        app.LoadWelcomePage();
    }
    @FXML
    private void hanldeCart(){
        app.LoadCartPage();
    }
    @FXML void hanldOrder(){
        app.LoadMyOrderPage();
    }
    public void setApp(App app) {
		this.app = app;
	}
}
