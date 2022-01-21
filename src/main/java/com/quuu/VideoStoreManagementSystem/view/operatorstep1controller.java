package com.quuu.VideoStoreManagementSystem.view;

import com.quuu.VideoStoreManagementSystem.App;
import com.quuu.VideoStoreManagementSystem.util.TempUser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class operatorstep1controller {
    @FXML
    private TextField address,name;
    @FXML
    private ChoiceBox<String> delivery,province;
    private ObservableList<String> values,values2;

    private App app;

    public operatorstep1controller(){
        values=FXCollections.observableArrayList("Self pick up", "Deliver to door");
        values2=FXCollections.observableArrayList("NL","PE","ON","NS","NB","QC","MB","SK","AB","BC","YT","NT","NU");
    }

    @FXML
    private void init(){
        delivery.setItems(values);
        province.setItems(values2);
    }

    @FXML
    private void handleNext(){
        TempUser u = new TempUser(address.getText(), name.getText(), delivery.getValue(),province.getValue());
        app.setTempuser(u);
        app.LoadFindMoviePage();
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
