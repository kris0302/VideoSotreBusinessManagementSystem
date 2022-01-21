package com.quuu.VideoStoreManagementSystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.quuu.VideoStoreManagementSystem.util.Movie;
import com.quuu.VideoStoreManagementSystem.util.TempUser;
import com.quuu.VideoStoreManagementSystem.view.AccountManagementPageController;
import com.quuu.VideoStoreManagementSystem.view.AddMoviePageController;
import com.quuu.VideoStoreManagementSystem.view.AdminNavPageController;
import com.quuu.VideoStoreManagementSystem.view.AdminOrderController;
import com.quuu.VideoStoreManagementSystem.view.CartPageController;
import com.quuu.VideoStoreManagementSystem.view.CustomerNavPageController;
import com.quuu.VideoStoreManagementSystem.view.FindMoviePageController;
import com.quuu.VideoStoreManagementSystem.view.ModifyDeleteCustomerAccountPage;
import com.quuu.VideoStoreManagementSystem.view.MovieModifyPageController;
import com.quuu.VideoStoreManagementSystem.view.MovieUpdatePageController;
import com.quuu.VideoStoreManagementSystem.view.MyOrderPageController;
import com.quuu.VideoStoreManagementSystem.view.OperatorNavPageController;
import com.quuu.VideoStoreManagementSystem.view.OperatorOrderPlacedPageController;
import com.quuu.VideoStoreManagementSystem.view.OrderPlacedController;
import com.quuu.VideoStoreManagementSystem.view.PaymentPageController;
import com.quuu.VideoStoreManagementSystem.view.RootPageController;
import com.quuu.VideoStoreManagementSystem.view.SignUpPageController;
import com.quuu.VideoStoreManagementSystem.view.SignUpSuccessPageController;
import com.quuu.VideoStoreManagementSystem.view.WareHousePageController;
import com.quuu.VideoStoreManagementSystem.view.WelcomePageController;
import com.quuu.VideoStoreManagementSystem.view.operatorstep1controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage; 

public final class App extends Application {

    private Stage primaryStage;
    private BorderPane masterLayout;
    private Connection connection;
    private String currentUser;
    private Movie tmpMovie;
    private TempUser tempuser;
    private int tempID;
    
    private void BuildConnection() {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.connection = DriverManager.getConnection("jdbc:mysql://212.1.208.1:3306/u928545403_VideoStore?useSSL=false&allowPublicKeyRetrieval=true&autoReconnect=true", "u928545403_root", "Abc123test");
			System.out.println("Connection to database Established");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
    }

    public int getTempID() {
        return tempID;
    }

    public void setTempID(int tempID) {
        this.tempID = tempID;
    }

    public TempUser getTempuser() {
        return tempuser;
    }

    public void setTempuser(TempUser tempuser) {
        this.tempuser = tempuser;
    }

    public void RebuildConnection() throws SQLException{
        if(!connection.isValid(0)){
            this.BuildConnection();
            System.out.println("Connection to database ReEstablished");
        }else{
            System.out.println("Connection valid");
        }
    }
    
    public Connection getConnection() {
    	return this.connection;
    }

    @Override
    public void start(Stage stage){
        this.primaryStage = stage;
        this.primaryStage.setWidth(500);
        this.primaryStage.setHeight(530);
        this.primaryStage.setTitle("My video store management system");

        PrepareMasterContainer();
        this.BuildConnection();
        LoadWelcomePage();
    }

    public void PrepareMasterContainer() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("view/MasterContainer.fxml"));
			masterLayout = (BorderPane) loader.load();
			//masterLayout = (BorderPane) FXMLLoader.load(getClass().getResource("view/mastercontainer.fxml"));
			
			Scene scene = new Scene(masterLayout);
			tmpMovie = new Movie();
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			System.out.println(e);
		}
		
	}

    public void LoadWelcomePage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/WelcomePage.fxml"));
			AnchorPane welcomePage = (AnchorPane) loader.load();
			
			masterLayout.setCenter(welcomePage);
			WelcomePageController controller = loader.getController();
			controller.setApp(this);
			//this.BuildConnection();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void LoadSignUpPage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/SignUpPage.fxml"));
			AnchorPane SignUpPage = (AnchorPane) loader.load();
			
			masterLayout.setCenter(SignUpPage);
			SignUpPageController controller = loader.getController();
			controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void LoadSignUpSuccessPage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/SignUpSuccessPage.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            SignUpSuccessPageController controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void LoadCustomerNavPage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/CustomerNavPage.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            CustomerNavPageController controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void LoadAccountManagementNavPage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/AccountManagementNavPage.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            AccountManagementPageController controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void LoadPaymentPage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/PaymentPage.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            PaymentPageController controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void LoadFindMoviePage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/FindMoviePage.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            FindMoviePageController controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void LoadCartPage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/CartPage.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            CartPageController controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void LoadOrderPlacedPage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/OrderPlaced.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            masterLayout.setCenter(page);
            OrderPlacedController controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void LoadMyOrderPage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/MyOrderPage.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            MyOrderPageController controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void LoadAdminNavPage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/AdminNavPage.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            AdminNavPageController controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void LoadAddMoviePage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/AddMoviePage.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            AddMoviePageController controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void LoadMovieModifyPage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/MovieModifyPage.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            MovieModifyPageController controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void LoadMovieUpdatePage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/MovieUpdatePage.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            MovieUpdatePageController controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void LoadModifyDeleteCustomerAccountPage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/ModifyDeleteCustomerAccountPage.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            ModifyDeleteCustomerAccountPage controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void LoadAdminOrderPage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/AdminOrder.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            AdminOrderController controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void LoadOperatorNavPage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/OperatorNavPage.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            OperatorNavPageController controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void LoadOperatorstep1Page() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/operatorstep1.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            operatorstep1controller controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void LoadOperatorOrderPlacedPage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/OperatorOrderPlacedPage.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            OperatorOrderPlacedPageController controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void LoadWarehousePage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/WarehousePage.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            WareHousePageController controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void LoadRootPage() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/RootPage.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			masterLayout.setCenter(page);
            RootPageController controller = loader.getController();
            controller.setApp(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void setCurrentUser(String s){
        this.currentUser = s;
    }
    public String getCurrentUser(){
        return currentUser;
    }
    public Movie getMovie(){
        return this.tmpMovie;
    }
    public void setMovie(Movie m){
        this.tmpMovie = m;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
