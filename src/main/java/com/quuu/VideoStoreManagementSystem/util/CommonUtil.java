package com.quuu.VideoStoreManagementSystem.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.quuu.VideoStoreManagementSystem.App;

public class CommonUtil {
    public static void createOrder(String user, float amount, App app) throws SQLException{
        Random random = new Random();
        ResultSet curUser = SQLutil.select(app.getConnection(), "*", "user", "email=\'"+app.getCurrentUser()+"\'");
        curUser.next();
        String curCart = curUser.getString("cart");
        String curAddress = curUser.getString("address");
        String province = curUser.getString("province");
        //String[] warehouse = {"warehouse1","warehouse2","warehouse3"};
        Date curDate = Calendar.getInstance().getTime();
        java.sql.Date sqlDate = new java.sql.Date(curDate.getTime());
        SQLutil.insertOrder(app.getConnection(), Integer.toString(random.nextInt(100000000)+10000000), curCart, user, sqlDate, amount, "Unpaid", curAddress,province, "/", "/");
    }

    public static int createOperatorOrder(TempUser u, float amount, App app) throws SQLException{
        Random random = new Random();
        ResultSet curUser = SQLutil.select(app.getConnection(), "*", "operator", "email=\'"+app.getCurrentUser()+"\'");
        curUser.next();
        String curCart = curUser.getString("cart");
        String curAddress = u.getAddress();
        //String[] warehouse = {"warehouse1","warehouse2","warehouse3"};
        Date curDate = Calendar.getInstance().getTime();
        java.sql.Date sqlDate = new java.sql.Date(curDate.getTime());
        int id = random.nextInt(100000000)+10000000;
        SQLutil.insertOrder(app.getConnection(), Integer.toString(id), curCart, u.getName(), sqlDate, amount, "Unpaid", u.getProvince(), curAddress, "/", u.getDelivery());
        return id;
    }
}
