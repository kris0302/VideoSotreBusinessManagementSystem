package com.quuu.VideoStoreManagementSystem.view;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.quuu.VideoStoreManagementSystem.App;
import com.quuu.VideoStoreManagementSystem.util.FXutil;
import com.quuu.VideoStoreManagementSystem.util.Movie;
import com.quuu.VideoStoreManagementSystem.util.SQLutil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MovieModifyPageController {
    @FXML
    private Button Filter,update,remove,back;

    @FXML
    private TextField title;
    @FXML
    private TableView<Movie> movietable;
    @FXML
    private ChoiceBox<String> cat;
    @FXML
    private Label prompt;

    private ObservableList<String> values;

    private App app;

    @FXML
    private void init() throws SQLException{
        values=FXCollections.observableArrayList("","cat1","cat2","cat3");
        cat.setItems(values);
        cat.setValue("");
        app.RebuildConnection();
        ResultSet resu = SQLutil.select(app.getConnection(), "*", "movie", "");
        FXutil.buildMovieTable(movietable, resu,"Search");
    }
    
    @FXML
    private void filter() throws SQLException{
        prompt.setText("");
        String name = title.getText();
        String category = cat.getValue();
        app.RebuildConnection();
        ResultSet resu = SQLutil.select(app.getConnection(), "*", "movie", "");
        movietable.getItems().clear();
        if(!name.isEmpty() && category.isEmpty()){
            resu = SQLutil.select(app.getConnection(), "*", "movie", "title=\'"+name+"\'");
        }
        if(name.isEmpty() && !category.isEmpty()){
            resu = SQLutil.select(app.getConnection(), "*", "movie", "cat=\'"+category+"\'");
        }
        if(!name.isEmpty() && !category.isEmpty()){
            resu = SQLutil.select(app.getConnection(), "*", "movie", "title=\'"+name+"\' AND cat=\'"+category+"\'");
        }
        FXutil.buildMovieTable(movietable, resu,"Search");
    }

    @FXML
    private void handleBack(){
        app.LoadAdminNavPage();
    }

    @FXML
    private void handleUpdate(){
        Movie selected = movietable.getSelectionModel().getSelectedItem();
        if(selected == null){
            prompt.setText("Please select a movie to update");
            return;
        }
        app.setMovie(selected);
        app.LoadMovieUpdatePage();
    }

    @FXML
    private void handleRemove() throws SQLException{
        app.RebuildConnection();
        Movie selected = movietable.getSelectionModel().getSelectedItem();
        if(selected == null){
            prompt.setText("Please select a movie to remove");
            return;
        }
        SQLutil.delete(app.getConnection(), "movie", "title=\'"+selected.getTitle()+"\'");
        prompt.setText("Movie "+selected.getTitle()+" removed!");
        init();
    }

    public void setApp(App app) throws SQLException{
		this.app = app;
        init();
	}
}
