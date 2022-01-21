package com.quuu.VideoStoreManagementSystem.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Random;

import com.quuu.VideoStoreManagementSystem.App;
import com.quuu.VideoStoreManagementSystem.util.SQLutil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class PaymentPageController {
    @FXML
    private ChoiceBox<String> delivery;
    @FXML
    private ChoiceBox<String> paytype;
    @FXML
    private Label amount;
    @FXML
    private TextField name;
    @FXML
    private TextField cardnumber;
    @FXML
    private HBox dblock,rblock;
    @FXML
    private DatePicker expirydate;
    @FXML
    private Label prompt,rp;
    @FXML
    private Button pay;
    @FXML
    private Button back;
    @FXML
    private CheckBox reedem;
    private ObservableList<String> values;
    private ObservableList<String> values2;
    private App app;
    private double tmpAmount;

    public PaymentPageController(){
        values=FXCollections.observableArrayList("Self pick up", "Deliver to door");
        values2=FXCollections.observableArrayList("Order fee", "Late fee");
    }

    @FXML
    private void init() throws SQLException{
        app.RebuildConnection();
        delivery.setItems(values);
        delivery.setValue("Deliver to door");
        paytype.setItems(values2);
        ResultSet resu = SQLutil.select(app.getConnection(), "*", "user", "email=\'"+app.getCurrentUser()+"\'");
        resu.next();
        double amountfee = resu.getDouble("amountdue");
        tmpAmount = amountfee;
        float latefee = resu.getFloat("latefee");
        int reward = resu.getInt("rewardpoints");
        rp.setText(Integer.toString(reward));
        if (reward >= 10) {
            reedem.setDisable(false);
        } else {
            reedem.setDisable(true);
        }
        if(amountfee!=0 && latefee==0){
            paytype.setValue("Order fee");
            paytype.setDisable(true);
            amount.setText("$"+amountfee);
        }else if(amountfee==0 && latefee!=0){
            paytype.setValue("Late fee");
            paytype.setDisable(true);
            delivery.setDisable(true);
            dblock.setVisible(false);
            rblock.setVisible(false);
            amount.setText("$"+latefee);
        }else if(amountfee!=0 && latefee!=0){
            amount.setText("Please select what fee do you want to pay");
            pay.setDisable(true);
            paytype.setOnAction((event) -> {
                if(paytype.getValue().equals("Late fee")){
                    amount.setText("$"+latefee);
                    delivery.setDisable(true);
                    reedem.setSelected(false);
                    dblock.setVisible(false);
                    rblock.setVisible(false);
                    pay.setDisable(false);
                }else{
                    amount.setText("$"+amountfee);
                    delivery.setDisable(false);
                    dblock.setVisible(true);
                    rblock.setVisible(true);
                    pay.setDisable(false);
                }
            });
        }
    }

    @FXML
    private void handleBack() {
        app.LoadAccountManagementNavPage();
    }

    @FXML
    private void handlePay() throws SQLException{
        app.RebuildConnection();
        if(cardnumber.getText().isEmpty() || name.getText().isEmpty() || expirydate.getValue()==null){
            prompt.setText("Please ensure no fields are empty");
        }else{
            if(!cardnumber.getText().matches("[0-9]{16}")){
                prompt.setText("Please enter a valid card number");
                return;
            }
            ResultSet curUser = SQLutil.select(app.getConnection(), "*", "user", "email=\'"+app.getCurrentUser()+"\'");
            curUser.next();
            String username = curUser.getString("firstname")+" "+curUser.getString("lastname");
            if(!name.getText().equals(username)){
                prompt.setText("Cardholder's name must match our record");
                return;
            }
            if(expirydate.getValue().isBefore(LocalDate.now())){
                prompt.setText("Card expiry date is invalid");
                return;
            }
            if(paytype.getValue().equals("Order fee")){
                Random random = new Random();
                String[] warehouse = {"warehouse1","warehouse2","warehouse3"};
                SQLutil.update(app.getConnection(), "orders", "delivery=\'"+delivery.getValue()+"\'", "customer=\'"+app.getCurrentUser()+"\' AND status=\'Unpaid\'");
                SQLutil.update(app.getConnection(), "orders", "warehouse=\'"+warehouse[random.nextInt(3)]+"\'", "customer=\'"+app.getCurrentUser()+"\' AND status=\'Unpaid\'");
                SQLutil.update(app.getConnection(), "orders", "status=\'Paid\'", "customer=\'"+app.getCurrentUser()+"\' AND status=\'Unpaid\'");
                SQLutil.update(app.getConnection(), "user", "amountdue=0", "email=\'"+app.getCurrentUser()+"\'");
                SQLutil.update(app.getConnection(), "user", "rewardpoints=rewardpoints+1", "email=\'"+app.getCurrentUser()+"\'");
                if(reedem.isSelected()){
                    SQLutil.update(app.getConnection(), "user", "rewardpoints=rewardpoints-10", "email=\'"+app.getCurrentUser()+"\'");
                }
                app.LoadMyOrderPage();
            }else{
                SQLutil.update(app.getConnection(), "user", "latefee=0", "email=\'"+app.getCurrentUser()+"\'");
                app.LoadAccountManagementNavPage();
            }
        }
    }

    @FXML
    private void handleReedem() throws SQLException{
        app.RebuildConnection();
        ResultSet orderset = SQLutil.select(app.getConnection(), "*", "orders", "customer=\'"+app.getCurrentUser()+"\' AND status=\'Unpaid\'");
        double high = 0;
        orderset.next();
        String[] titles = orderset.getString("title").split(",");
        for(String s: titles){
            ResultSet thisTitle = SQLutil.select(app.getConnection(), "price", "movie", "title=\'"+s+"\'");
            thisTitle.next();
            if(Double.parseDouble(thisTitle.getString("price")) > high){
                high = Double.parseDouble(thisTitle.getString("price"));
            }
        } 
        DecimalFormat df = new DecimalFormat("0.00");
        if(reedem.isSelected()){
            double thisAmount = tmpAmount - high;
            tmpAmount = thisAmount;
            amount.setText("$" + df.format(thisAmount));
        }else{
            double thisAmount = tmpAmount + high;
            tmpAmount = thisAmount;
            amount.setText("$" + df.format(thisAmount));
        }
    }

    public void setApp(App app) throws SQLException{
		this.app = app;
        init();
	}
}
