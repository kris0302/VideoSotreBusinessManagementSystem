package com.quuu.VideoStoreManagementSystem.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.quuu.VideoStoreManagementSystem.App;
import com.quuu.VideoStoreManagementSystem.util.SQLutil;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RootPageController {
    @FXML
    private Button update, logoff;
    @FXML
    private Label prompt;
    private App app;

    @FXML
    private void handleLogoff(){
        app.LoadWelcomePage();
    }

    @FXML
    private void handleUpdate() throws SQLException{
        app.RebuildConnection();
        ResultSet orders = SQLutil.select(app.getConnection(), "*", "orders", "");
        if(!orders.isBeforeFirst()){
            prompt.setText("There's no order in databse");
        }else{
            while(orders.next()){
                if(orders.getString("status").equals("Delivered")){
                    Date orderdate = orders.getDate("orderdate");
                    Date current = new Date(System.currentTimeMillis());
                    long between = current.getTime() - orderdate.getTime();
                    long diff = TimeUnit.DAYS.convert(between, TimeUnit.MILLISECONDS);
                    int intdiff = (int) diff;
                    System.out.println(intdiff);
                    if(orders.getString("customer").matches(".*@.*\\.com")){
                        if(diff>14){
                            int latedays = intdiff-14;
                            int moviecount=orders.getString("title").split(",").length;
                            SQLutil.update(app.getConnection(), "orders", "latedays="+Integer.toString(latedays), "id=\'"+orders.getString("id")+"\'");
                            if(orders.getString("province").equals("ON")){
                                float latefee = (float) latedays;
                                SQLutil.update(app.getConnection(), "user", "latefee=latefee+"+ Float.toString(moviecount*latefee) , "email=\'"+orders.getString("customer")+"\'");
                            }
                        }else{
                            SQLutil.update(app.getConnection(), "user", "latefee=latefee+9.99", "email=\'"+orders.getString("customer")+"\'");
                        }
                    }
                    
                }
            }
        }
    }

    public void setApp(App app) {
		this.app = app;
	}
}
