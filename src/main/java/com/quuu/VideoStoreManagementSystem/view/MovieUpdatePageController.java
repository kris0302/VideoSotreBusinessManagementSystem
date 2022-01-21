package com.quuu.VideoStoreManagementSystem.view;

import java.sql.SQLException;

import com.quuu.VideoStoreManagementSystem.App;
import com.quuu.VideoStoreManagementSystem.util.Movie;
import com.quuu.VideoStoreManagementSystem.util.SQLutil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MovieUpdatePageController {
    @FXML
    private Button update, back;

    @FXML
    private TextField title,director,actor,description,price,avail;
    @FXML
    private ChoiceBox<String> cat;
    @FXML
    private Label prompt;
    private App app;
    private ObservableList<String> values;
    private Movie curMovie;

    @FXML
    private void init(){
        values=FXCollections.observableArrayList("cat1","cat2", "cat3");
        curMovie = app.getMovie();
        cat.setItems(values);
        cat.setValue(curMovie.getCat());
        title.setText(curMovie.getTitle());
        director.setText(curMovie.getDirector());
        actor.setText(curMovie.getActors());
        description.setText(curMovie.getDescription());
        price.setText(String.valueOf(curMovie.getPrice()));
        avail.setText(String.valueOf(curMovie.getAvail()));
    }

    @FXML
    private void handleBack(){
        app.LoadMovieModifyPage();
    }

    @FXML
    private void handleUpdate() throws SQLException{
        app.RebuildConnection();
        SQLutil.delete(app.getConnection(), "movie", "title=\'"+curMovie.getTitle()+"\'");
        SQLutil.insertMovie(app.getConnection(), title.getText(), cat.getValue(), director.getText(), actor.getText(), description.getText(), Double.parseDouble(price.getText()), Integer.parseInt(avail.getText()));
        /* prompt.setText("Movie updated!");
        cat.setValue("");
        title.setText("");
        director.setText("");
        actor.setText("");
        description.setText("");
        price.setText("");
        avail.setText(""); */
        app.LoadMovieModifyPage();
    }

    public void setApp(App app){
		this.app = app;
        init();
	}

}
