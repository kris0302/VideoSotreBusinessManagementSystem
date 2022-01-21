package com.quuu.VideoStoreManagementSystem.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLutil {
	/*private static Connection connection;
	private static SQLutil instance=null;
	
	private SQLutil() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.connection = DriverManager.getConnection("jdbc:mysql://212.1.208.1:3306/u928545403_VideoStore?useSSL=false&allowPublicKeyRetrieval=true", "u928545403_root", "Abc123test");
			System.out.println("Connection to database Established");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public static SQLutil getInstance() {
		if(instance == null) {
			instance = new SQLutil();
		}
		return instance;
	}*/
	
	public static ResultSet select(Connection connection,String what, String from, String where) {
		ResultSet resultSet = null;
		try {
			Statement statement = connection.createStatement();
			String query = "select "+what+" from "+from;
			if(where.length() != 0) {
				query = query + " where "+where;
			}
			System.out.println("SQL query executed: "+query);
			resultSet = statement.executeQuery(query);
		} catch (Exception e) {
			System.out.println(e);
		}
		return resultSet;
	}

	public static void insertUser(Connection connection,String[] values) throws SQLException{
		String query = "insert into user values (?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, values[0]);
		statement.setString(2, values[1]);
		statement.setString(3, values[2]);
		statement.setString(4, values[3]);
		statement.setString(5, values[4]);
		statement.setString(6, values[5]);
		statement.setInt(7, Integer.parseInt(values[6]));
		statement.setDouble(8, Double.parseDouble(values[7]));
		statement.setString(9, "");
		statement.setFloat(10, 0);
		System.out.println("SQL query executed: "+"insert into user values ("+values[0]+","+values[1]+","+values[2]+","+values[3]+","+values[4]+","+values[5]+","+values[6]+","+values[7]+","+"null,0)");
		statement.execute();
	}

	public static void insertOrder(Connection connection,String id, String title, String customer, Date orderdate, float amount, String status, String address,String province,  String warehouse, String delivery) throws SQLException{
		String query = "insert into orders values (?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, id);
		statement.setString(2, title);
		statement.setString(3, customer);
		statement.setDate(4, orderdate);
		statement.setFloat(5, amount);
		statement.setString(6, status);
		statement.setString(7, address);
		statement.setString(8, province);
		statement.setInt(9, 0);
		statement.setString(10, warehouse);
		statement.setString(11, delivery);
		System.out.println("SQL query executed: " + "insert into orders values (" + id + "," + title + "," + customer
				+ "," + orderdate + "," + amount + "," + status + "," + address + ","+province+",0" + warehouse + ",/)");
		statement.execute();
	}

	public static void insertAdminWarehouseadmin(Connection connection, String email, String password, String type) throws SQLException{
		String query = "insert into " +type+ " values (?,?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, email);
		statement.setString(2, password);
		System.out.println("SQL query executed: "+"insert into "+type+" values ("+email+","+password+")");
		statement.execute();
	}

	public static void insertOperator(Connection connection, String email, String password) throws SQLException{
		String query = "insert into " +"operator"+ " values (?,?,?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, email);
		statement.setString(2, password);
		statement.setString(3, "");
		System.out.println("SQL query executed: "+"insert into "+"operator"+" values ("+email+","+password+",\'\'')");
		statement.execute();
	}

	public static void insertMovie(Connection connection, String title, String cat, String director,String actors, String description, double price, int avail) throws SQLException{
		String query = "insert into movie values (?,?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, title);
		statement.setString(2, cat);
		statement.setString(3, director);
		statement.setString(4, actors);
		statement.setString(5, description);
		statement.setDouble(6, price);
		statement.setInt(7, avail);
		System.out.println("SQL query executed: "+"insert into movie values ("+title+","+cat+","+director+","+actors+","+description+","+price+","+avail+ ")");
		statement.execute();
	}

	public static void update(Connection connection, String what, String set, String where) throws SQLException{
		String query = "UPDATE "+what+" SET "+set+" WHERE "+where;
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		System.out.println("SQL query executed: "+query);
	}

	public static void delete(Connection connection, String from, String where) throws SQLException{
		String query = "Delete from "+from+" where "+where;

		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		System.out.println("SQL query executed: "+query);
	}

	
}
