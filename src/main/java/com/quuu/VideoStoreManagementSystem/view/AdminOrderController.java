package com.quuu.VideoStoreManagementSystem.view;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.quuu.VideoStoreManagementSystem.App;
import com.quuu.VideoStoreManagementSystem.util.FXutil;
import com.quuu.VideoStoreManagementSystem.util.Order;
import com.quuu.VideoStoreManagementSystem.util.SQLutil;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AdminOrderController {
    @FXML
    private Button search,unpaid,paid,outford,delivered,pickup,todoor,back,returned;
    @FXML
    private TextField id;
    @FXML
    private TableView<Order> ordertable;
    @FXML
    private Label prompt;
    @FXML
    private VBox box1,box2;

    private App app;

    @FXML
    private void init() throws SQLException{
        app.RebuildConnection();
        if(app.getCurrentUser().matches(".*@videostoreoperator.com")){
            box1.setVisible(false);
            box2.setVisible(false);
        }else{
            box1.setVisible(true);
            box2.setVisible(true);
        }
        ResultSet resu = SQLutil.select(app.getConnection(), "*", "orders", "");
        FXutil.buildOrderTable(ordertable, resu);
    }

    @FXML
    private void handleSearch() throws SQLException{
        app.RebuildConnection();
        if(id.getText().isEmpty()){
            init();
            return;
        }
        ResultSet resu = SQLutil.select(app.getConnection(), "*", "orders", "id=\'"+id.getText()+"\'");
        FXutil.buildOrderTable(ordertable, resu);
        
    }

    @FXML
    private boolean testSelection(){
        prompt.setText("");
        if(ordertable.getSelectionModel().getSelectedItem() == null){
            prompt.setText("Select order to modify");
            return false;
        }else{
            return true;
        }
    }
    @FXML
    private void setPaid() throws SQLException{
        if(!testSelection()){
            return;
        }
        app.RebuildConnection();
        SQLutil.update(app.getConnection(), "orders", "status=\'Paid\'", "id=\'"+ordertable.getSelectionModel().getSelectedItem().getId()+"\'");
        init();
    }
    @FXML
    private void setUnpaid() throws SQLException{
        if(!testSelection()){
            return;
        }
        app.RebuildConnection();
        SQLutil.update(app.getConnection(), "orders", "status=\'Unpaid\'", "id=\'"+ordertable.getSelectionModel().getSelectedItem().getId()+"\'");
        init();
    }
    @FXML
    private void setOUtford() throws SQLException{
        if(!testSelection()){
            return;
        }
        app.RebuildConnection();
        SQLutil.update(app.getConnection(), "orders", "status=\'Out for delivery\'", "id=\'"+ordertable.getSelectionModel().getSelectedItem().getId()+"\'");
        init();
    }
    @FXML
    private void setDelivered() throws SQLException{
        if(!testSelection()){
            return;
        }
        app.RebuildConnection();
        SQLutil.update(app.getConnection(), "orders", "status=\'Delivered\'", "id=\'"+ordertable.getSelectionModel().getSelectedItem().getId()+"\'");
        init();
    }
    @FXML
    private void setReturned() throws SQLException{
        if(!testSelection()){
            return;
        }
        app.RebuildConnection();
        SQLutil.update(app.getConnection(), "orders", "status=\'Returned\'", "id=\'"+ordertable.getSelectionModel().getSelectedItem().getId()+"\'");
        init();
    }
    @FXML
    private void setPickup() throws SQLException{
        if(!testSelection()){
            return;
        }
        app.RebuildConnection();
        SQLutil.update(app.getConnection(), "orders", "delivery=\'Self pick up\'", "id=\'"+ordertable.getSelectionModel().getSelectedItem().getId()+"\'");
        init();
    }
    @FXML
    private void setTodoor() throws SQLException{
        if(!testSelection()){
            return;
        }
        app.RebuildConnection();
        SQLutil.update(app.getConnection(), "orders", "delivery=\'Deliver to door\'", "id=\'"+ordertable.getSelectionModel().getSelectedItem().getId()+"\'");
        init();
    }

    @FXML
    private void handleBack(){
        if(app.getCurrentUser().matches(".*@videostoreoperator.com")){
            app.LoadOperatorNavPage();
        }else{
            app.LoadAdminNavPage();
        }
    }

    public void setApp(App app) throws SQLException{
		this.app = app;
        init();
	}
}
