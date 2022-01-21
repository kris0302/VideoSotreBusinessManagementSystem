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

public class WareHousePageController {
    @FXML
    private TextField id;
    @FXML
    private Button search,ship,back;
    @FXML
    private TableView<Order> ordertable;
    @FXML
    private Label prompt;

    private App app;

    @FXML
    private void init() throws SQLException{
        app.RebuildConnection();

        ResultSet resu = SQLutil.select(app.getConnection(), "*", "orders", "status=\'Paid\'");
        FXutil.buildOrderTable(ordertable, resu);
    }

    @FXML
    private void handleSearch() throws SQLException{
        app.RebuildConnection();
        prompt.setText("");
        if(id.getText().isEmpty()){
            init();
            return;
        }
        ResultSet resu = SQLutil.select(app.getConnection(), "*", "orders", "id=\'"+id.getText()+"\'");
        FXutil.buildOrderTable(ordertable, resu);
        
    }

    @FXML
    private void handleShip() throws SQLException{
        app.RebuildConnection();
        prompt.setText("");
        if(ordertable.getSelectionModel().getSelectedItem() == null){
            prompt.setText("Select order to ship");
            return;
        }
        SQLutil.update(app.getConnection(), "orders", "status=\'Out for delivery\'", "id=\'"+ordertable.getSelectionModel().getSelectedItem().getId()+"\'");
        init();
    }

    @FXML
    private void handleBack() throws SQLException{
        prompt.setText("Selected order shipped!");
        app.LoadWelcomePage();
    }

    public void setApp(App app) throws SQLException{
		this.app = app;
        init();
	}
}
