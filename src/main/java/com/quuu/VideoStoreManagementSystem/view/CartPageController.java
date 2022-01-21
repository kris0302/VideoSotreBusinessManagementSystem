package com.quuu.VideoStoreManagementSystem.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.quuu.VideoStoreManagementSystem.App;
import com.quuu.VideoStoreManagementSystem.util.CommonUtil;
import com.quuu.VideoStoreManagementSystem.util.FXutil;
import com.quuu.VideoStoreManagementSystem.util.Movie;
import com.quuu.VideoStoreManagementSystem.util.SQLutil;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class CartPageController {
    @FXML
    private TableView<Movie> movietable;
    @FXML
    private Label total;
    @FXML
    private Label quantity;
    @FXML
    private Button remove;
    @FXML
    private Button pay;
    @FXML
    private Button back;
    @FXML
    private Label prompt,headline;

    private App app;
    private float amount;
    private int q;

    @FXML
    private void init() throws SQLException{
        app.RebuildConnection();
        if(app.getCurrentUser().matches(".*@videostoreoperator.com")){
            headline.setText("Step 3: Check cart and place order");
        }else{
            headline.setText("Find Movie");
        }
        quantity.setText("Quantity: 0");
        total.setText("Amount: $0.00");
        amount = 0;
        q = 0;
        ResultSet curUser = null;
        if(app.getCurrentUser().matches(".*@videostoreoperator.com")){
            curUser = SQLutil.select(app.getConnection(), "*", "operator", "email=\'"+app.getCurrentUser()+"\'");
        }else{
            curUser = SQLutil.select(app.getConnection(), "*", "user", "email=\'"+app.getCurrentUser()+"\'");
        }
        curUser.next();
        String curCart = curUser.getString("cart");
        if(curCart.isEmpty())
            return;
        String[] parsedCart = curCart.split(",");
        q = parsedCart.length;
        String where = "title=\'"+parsedCart[0]+"\'";
        for(int i=1;i<parsedCart.length;i++){
            where = where + " OR title=\'"+parsedCart[i]+"\'";
        }
        ResultSet resu= SQLutil.select(app.getConnection(), "*", "movie", where);
        FXutil.buildMovieTable(movietable, resu, "cart");
        resu= SQLutil.select(app.getConnection(), "*", "movie", where);
        while(resu.next()){
            amount += Float.parseFloat(resu.getString("price"));
        }
        DecimalFormat df = new DecimalFormat("0.00");
        quantity.setText("Quantity: "+q);
        total.setText("Amount: $"+df.format(amount));
    }

    @FXML
    private void handleRemove() throws SQLException{
        app.RebuildConnection();
        Movie selected = movietable.getSelectionModel().getSelectedItem();
        if(selected == null){
            prompt.setText("Please select a movie to remove");
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
        ArrayList<String> parsedCartList = new ArrayList<>();
        for(String s:parsedCart){
            if(!s.equals(selected.getTitle())){
                parsedCartList.add(s);
            }
        }
        String newCart="";
        for(int i=0;i<parsedCartList.size()-1;i++){
            newCart += parsedCart+",";
        }
        if (parsedCart.length > 1)
            newCart += parsedCart[parsedCart.length - 1];
        if (app.getCurrentUser().matches(".*@videostoreoperator.com")) {
            SQLutil.update(app.getConnection(), "operator", "cart=\'" + newCart + "\'",
                    "email=\'" + app.getCurrentUser() + "\'");
        } else {
            SQLutil.update(app.getConnection(), "user", "cart=\'" + newCart + "\'",
                    "email=\'" + app.getCurrentUser() + "\'");
        }
        SQLutil.update(app.getConnection(), "movie", "availablity="+(selected.getAvail()+1), "title=\'"+selected.getTitle()+"\'");
        movietable.getItems().clear();
        init();
    }

    @FXML
    private void handleback(){
        app.LoadFindMoviePage();
    }

    @FXML
    private void hanldePlaceOrder() throws SQLException{
        app.RebuildConnection();
        ResultSet curUser = null;
        if(app.getCurrentUser().matches(".*@videostoreoperator.com")){
            curUser = SQLutil.select(app.getConnection(), "*", "operator", "email=\'"+app.getCurrentUser()+"\'");
        }else{
            curUser = SQLutil.select(app.getConnection(), "*", "user", "email=\'"+app.getCurrentUser()+"\'");
        }
        curUser.next();
        if(curUser.getString("cart").isEmpty()){
            prompt.setText("There's nothing in the cart!");
            return;
        }
        ResultSet orderset = SQLutil.select(app.getConnection(), "*", "orders", "customer=\'"+app.getCurrentUser()+"\' AND status=\'Unpaid\'");
        if(orderset.isBeforeFirst()){
            prompt.setText("You have unpaid order! Please go to My Orders to pay!");
            return;
        }

        if(app.getCurrentUser().matches(".*@videostoreoperator.com")){
            app.setTempID(CommonUtil.createOperatorOrder(app.getTempuser(), amount, app));
            SQLutil.update(app.getConnection(), "operator", "cart=\'\'", "email=\'"+app.getCurrentUser()+"\'");
            app.LoadOperatorOrderPlacedPage();
        }else{
            SQLutil.update(app.getConnection(), "user", "amountdue="+amount, "email=\'"+app.getCurrentUser()+"\'");
            CommonUtil.createOrder(app.getCurrentUser(), amount, app);
            SQLutil.update(app.getConnection(), "user", "cart=\'\'", "email=\'"+app.getCurrentUser()+"\'");
            app.LoadOrderPlacedPage();
        }
        
        
    }

    public void setApp(App app) throws SQLException{
		this.app = app;
        init();
	}
}
