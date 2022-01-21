package com.quuu.VideoStoreManagementSystem.view;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.quuu.VideoStoreManagementSystem.App;
import com.quuu.VideoStoreManagementSystem.util.FXutil;
import com.quuu.VideoStoreManagementSystem.util.Order;
import com.quuu.VideoStoreManagementSystem.util.SQLutil;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class MyOrderPageController {
    @FXML
    private TableView<Order> ordertable;
    @FXML
    private Button cancel;
    @FXML
    private Button pay;
    @FXML
    private Button back;
    @FXML
    private Label prompt;

    private App app;

    @FXML
    private void init() throws SQLException{
        app.RebuildConnection();
        ResultSet resu = SQLutil.select(app.getConnection(), "*", "orders", "customer=\'"+app.getCurrentUser()+"\'");
        FXutil.buildOrderTable(ordertable, resu);
    }
    @FXML
    private void handleBack(){
        app.LoadCustomerNavPage();
    }

    @FXML
    private void handlePay(){
        ObservableList<Order> cur = ordertable.getItems();
        boolean flag = false;
        for(Order o : cur){
            if(o.getStatus().equals("Unpaid")){
                flag = true;
                break;
            }
        }
        if(ordertable.getItems().isEmpty() || flag == false){
            prompt.setText("Nothing to Pay! Please go to my account to check if you have outstanding late fee.");
        }else{
            app.LoadPaymentPage();
        }
        
    }

    @FXML
    private void handleCancel() throws SQLException{
        app.RebuildConnection();
        Order selected = ordertable.getSelectionModel().getSelectedItem();
        if(selected == null){
            prompt.setText("Please select a order to remove");
            return;
        }
        if(selected.getStatus().equals("Out for delivery") || selected.getStatus().equals("Delivered")){
            prompt.setText("Delievered orders cannot be cancelled");
            return;
        }
        SQLutil.delete(app.getConnection(), "orders", "id=\'"+selected.getId()+"\'");
        SQLutil.update(app.getConnection(), "user", "amountdue=amountdue-"+selected.getAmount(), "email=\'"+app.getCurrentUser()+"\'");
        ResultSet resu = SQLutil.select(app.getConnection(), "amountdue", "user", "email=\'"+app.getCurrentUser()+"\'");
        resu.next();
        if(resu.getDouble("amountdue")<0){
            SQLutil.update(app.getConnection(), "user", "amountdue=0", "email=\'"+app.getCurrentUser()+"\'");
        }
        String[] titles = selected.getTitle().split(",");
        for(String s : titles){
            SQLutil.update(app.getConnection(), "movie", "availablity=availablity+1", "title=\'"+s+"\'");
        }
        init();
    }

    public void setApp(App app) throws SQLException{
		this.app = app;
        init();
	}
}
