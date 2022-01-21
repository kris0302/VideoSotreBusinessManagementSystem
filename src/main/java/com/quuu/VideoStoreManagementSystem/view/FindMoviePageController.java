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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class FindMoviePageController {
    @FXML
    private TextField title;
    @FXML
    private ChoiceBox<String> cat;
    @FXML
    private Button filter;
    @FXML
    private Button incart;
    @FXML
    private Button back;
    @FXML
    private TableView<Movie> movietable;
    private ObservableList<String> values;
    @FXML
    private Label prompt,headline;
    private App app;

    public FindMoviePageController(){
        values=FXCollections.observableArrayList("","cat1","cat2","cat3");
    }

    @FXML
    private void init() throws SQLException{
        if(app.getCurrentUser().matches(".*@videostoreoperator.com")){
            headline.setText("Step 2: Add movie into cart");
        }else{
            headline.setText("Find Movie");
        }
        cat.setItems(values);
        cat.setValue("");
        app.RebuildConnection();
        ResultSet resu = SQLutil.select(app.getConnection(), "*", "movie", "");
        FXutil.buildMovieTable(movietable, resu,"Search");
    }

    @FXML
    private void handleFilter() throws SQLException{
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
    private void handleAddtocart() throws SQLException{
        app.RebuildConnection();
        Movie selected = movietable.getSelectionModel().getSelectedItem();
        if(selected == null){
            prompt.setText("Please select a movie");
            return;
        }
        if(selected.getAvail() == 0){
            prompt.setText("Selected movie is not available");
            return;
        }
        ResultSet curUser = null;
        if(app.getCurrentUser().matches(".*@videostoreoperator.com")){
            curUser = SQLutil.select(app.getConnection(), "*", "operator", "email=\'"+app.getCurrentUser()+"\'");
        }else{
            curUser = SQLutil.select(app.getConnection(), "*", "user", "email=\'"+app.getCurrentUser()+"\'");
        }
        
        curUser.next();
        String curCart = curUser.getString("cart");
        String[] parsedCart = curCart.split(",");
        for(String s : parsedCart){
            if(s.equals(selected.getTitle())){
                prompt.setText("This movie is already in your cart!");
                return;
            }
        }
        if(curCart.isEmpty()){
            curCart = selected.getTitle();
        }else{
            curCart = curCart+","+selected.getTitle();
        }
        if(app.getCurrentUser().matches(".*@videostoreoperator.com")){
            SQLutil.update(app.getConnection(), "operator", "cart=\'"+curCart+"\'", "email=\'"+app.getCurrentUser()+"\'");
        }else{
            SQLutil.update(app.getConnection(), "user", "cart=\'"+curCart+"\'", "email=\'"+app.getCurrentUser()+"\'");
        }
        
        SQLutil.update(app.getConnection(), "movie", "availablity="+(selected.getAvail()-1), "title=\'"+selected.getTitle()+"\'");
        app.LoadCartPage();
    }

    @FXML
    private void handleBack(){
        app.LoadCustomerNavPage();
    }

    @FXML
    private void handleGotoCart(){
        app.LoadCartPage();
    }

    public void setApp(App app) throws SQLException{
		this.app = app;
        init();
	}
}
