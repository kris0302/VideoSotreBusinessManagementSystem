package com.quuu.VideoStoreManagementSystem.view;

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

public class AddMoviePageController {
    @FXML
    private Button add,back;
    @FXML
    private ChoiceBox<String> cat;
    @FXML
    private Label prompt;
    @FXML
    private TextField title,director,actors,description,price,avail;

    private App app;
    private ObservableList<String> values;

    public AddMoviePageController(){
        values=FXCollections.observableArrayList("cat1","cat2", "cat3");
    }

    @FXML
    private void initialize(){
        cat.setItems(values);
        cat.setValue("cat1");
    }

    @FXML
    private void handleback(){
        app.LoadAdminNavPage();
    }

    @FXML
    private void handleAdd() throws SQLException{
        app.RebuildConnection();
        prompt.setText("Movie "+title.getText()+ " added!");
        SQLutil.insertMovie(app.getConnection(), title.getText(), cat.getValue(), director.getText(), actors.getText(), description.getText(), Double.parseDouble(price.getText()), Integer.parseInt(avail.getText()));
        title.clear();
        cat.setValue("");
        director.clear();;
        actors.clear();
        description.clear();
        price.clear();
        avail.clear();
    }

    public void setApp(App app){
		this.app = app;
	}
}
