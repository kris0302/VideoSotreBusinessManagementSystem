package com.quuu.VideoStoreManagementSystem.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXutil {
    @FXML
    public static void buildMovieTable(TableView<Movie> movietable, ResultSet resu, String flag) throws SQLException{
        movietable.getColumns().clear();
        movietable.getItems().clear();
        TableColumn<Movie, String> titlecolumn = new TableColumn<>("Title");
        TableColumn<Movie, String> directorcolumn = new TableColumn<>("Direcotr");
        TableColumn<Movie, String> actorcolumn = new TableColumn<>("Actors");
        TableColumn<Movie, String> catcolumn = new TableColumn<>("Category");
        if(flag.equals("Search")){
            TableColumn<Movie, String> availcolumn = new TableColumn<>("Availability");
            availcolumn.setCellValueFactory(new PropertyValueFactory<>("avail"));
            movietable.getColumns().add(availcolumn);
        }
        TableColumn<Movie, String> pricecolumn = new TableColumn<>("Price");
        TableColumn<Movie, String> descolumn = new TableColumn<>("Description");
        titlecolumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        directorcolumn.setCellValueFactory(new PropertyValueFactory<>("director"));
        actorcolumn.setCellValueFactory(new PropertyValueFactory<>("actors"));
        catcolumn.setCellValueFactory(new PropertyValueFactory<>("cat"));
        pricecolumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        descolumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        movietable.getColumns().add(titlecolumn);
        movietable.getColumns().add(directorcolumn);
        movietable.getColumns().add(actorcolumn);
        movietable.getColumns().add(catcolumn);
        movietable.getColumns().add(pricecolumn);
        movietable.getColumns().add(descolumn);
        

        Movie m = null;
        if(!resu.isBeforeFirst()){
            movietable.getItems().clear();
        }else{
            while(resu.next()){
                m = new Movie(resu.getString(1), resu.getString(2), resu.getString(3), resu.getString(4), resu.getString(5), Double.parseDouble(resu.getString(6)), Integer.parseInt(resu.getString(7)));
                movietable.getItems().add(m);
            }
        }
    }

    @FXML
    public static void buildOrderTable(TableView<Order> ordertable, ResultSet resu) throws SQLException{
        ordertable.getColumns().clear();
        ordertable.getItems().clear();
        TableColumn<Order, String> idcolumn = new TableColumn<>("Order ID");
        TableColumn<Order, String> titlecolumn = new TableColumn<>("Titles");
        TableColumn<Order, String> orderdatecolumn = new TableColumn<>("Order Date");
        TableColumn<Order, String> addresscolumn = new TableColumn<>("Address");
        TableColumn<Order, String> amountcolumn = new TableColumn<>("Total amount");
        TableColumn<Order, String> statuscolumn = new TableColumn<>("status");
        TableColumn<Order, String> deliverycolumn = new TableColumn<>("delivery Method");
        TableColumn<Order, String> latedayscolumn = new TableColumn<>("Late return days");
        idcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titlecolumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        orderdatecolumn.setCellValueFactory(new PropertyValueFactory<>("orderdate"));
        amountcolumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        statuscolumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        deliverycolumn.setCellValueFactory(new PropertyValueFactory<>("delivery"));
        addresscolumn.setCellValueFactory(new PropertyValueFactory<>("fulladdress"));
        latedayscolumn.setCellValueFactory(new PropertyValueFactory<>("latedays"));
        ordertable.getColumns().add(idcolumn);
        ordertable.getColumns().add(titlecolumn);
        ordertable.getColumns().add(addresscolumn);
        ordertable.getColumns().add(orderdatecolumn);
        ordertable.getColumns().add(amountcolumn);
        ordertable.getColumns().add(statuscolumn);
        ordertable.getColumns().add(deliverycolumn);
        ordertable.getColumns().add(latedayscolumn);

        Order o = null;
        if(!resu.isBeforeFirst()){
            ordertable.getItems().clear();
        }else{
            while(resu.next()){
                o = new Order(resu.getString(1), resu.getString(2), resu.getString(4), resu.getString(5), resu.getString(6), resu.getString(11), resu.getString(7),resu.getString(8),Integer.parseInt(resu.getString(9)) );
                ordertable.getItems().add(o);
            }
        }
    }
}
