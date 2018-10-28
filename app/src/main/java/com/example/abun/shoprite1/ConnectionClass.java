package com.example.abun.shoprite1;

/**
 * Created by Abun on 5/15/2018.
 */
import java.lang.String;
import java.io.File;
import android.annotation.SuppressLint;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import static java.nio.file.Files.size;
import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.StrictMode;
import android.util.Log;


public class ConnectionClass {

    String url;
    String serverName;
    String instanceName;
    String databaseName;
    String userName;
    String password;
    String port;
    String sql;

    ConnectionClass() {

        serverName = "192.168.80.1";
        port ="1433";
        //instanceName = "";
        databaseName = "shoprite";
        userName = "abun2";
        password = "shoprite";
    }
    private String getConnectionUrl() {
        // Constructing the connection string
        return url + serverName +" ;DatabaseName = " +databaseName +";user="+userName + ";password="+password +";";
    }

    //private String getConnectionUrl() {
    // Constructing the connection string
    //return url + serverName  +" ;DatabaseName = " +databaseName+"; integratedSecurity=true";
    //}

    @SuppressLint("NewApi")
    public Connection getConnection() throws SQLException, ClassNotFoundException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ConnUrl;
        //String conny= "jdbc:jtds:sqlserver://"+ serverName+ ":" +port+ ";"+ " ;DatabaseName = " +databaseName+"; integratedSecurity=true";
        ConnUrl="jdbc:jtds:sqlserver://" + serverName+ ":" +port+ ";"+ "databaseName=" + databaseName + ";user=" + userName + ";password=" + password + ";";
        Connection con =null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(ConnUrl);
            //con = DriverManager.getConnection(conny);
            //""+"jdbc:jtds:sqlserver://127.0.0.1/shoprite; integrated security=true"
            // Establishing the connection
            //con = DriverManager.getConnection(getConnectionUrl());
            //con = DriverManager.getConnection(ConnUrl);
            if(con != null){
                System.out.println("Connection Successful!");
            }
            else{
                System.out.println(" no Connection ");
            }
        }catch(SQLException se){
            Log.e("ERROR--",se.getMessage());
            Log.e("ERROR--",se.getSQLState());
            Log.e("ERROR--",se.getLocalizedMessage());
            Log.e("ERROR--",se.getCause().toString());
            Log.e("ERROR--",se.getStackTrace().toString());

        }catch(ClassNotFoundException se){
            Log.e("ERROR--",se.getMessage());
            Log.e("ERROR--",se.getLocalizedMessage());

        }catch(Exception se){
            Log.e("ERROR--",se.getMessage());
            Log.e("ERROR--",se.getLocalizedMessage());

        }
        return con;
    }

    public boolean verifyCustomer(String username,String password)throws SQLException{
        Connection con1;
        ResultSet resultSet = null;
        String query = "select * from Customer_Details where Customer_Name =? and Password=?";
        try {
            con1 = getConnection();
            PreparedStatement stmt = con1.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, username);
            stmt.setString(2, password);

            resultSet = stmt.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return false;
        } finally {

            if(resultSet != null)
                resultSet.close();
        }
    }

    public boolean verifyEmployee(String username,String password)throws SQLException{
        Connection con1;
        ResultSet resultSet = null;
        String query = "select * from Employee_Details where Employee_Name =? and Designation=?";
        try {
            con1 = getConnection();
            PreparedStatement stmt = con1.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, username);
            stmt.setString(2, password);

            resultSet = stmt.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return false;
        } finally {

            if(resultSet != null)
                resultSet.close();
        }
    }

    public boolean verifyAdmin(String username,String password)throws SQLException{
        Connection con1;
        ResultSet resultSet = null;
        String query = "select * from Admin_Table where Admin_Name =? and Designation=?";
        try {
            con1 = getConnection();
            PreparedStatement stmt = con1.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, username);
            stmt.setString(2, password);

            resultSet = stmt.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return false;
        } finally {

            if(resultSet != null)
                resultSet.close();
        }
    }

    public boolean insertDataCustomer(String name, String gender,String address,String phone,
                                      String DOB,String password,String email,int creditcard,String creditCardType){
        Connection con1;
        String query = "insert into Customer_Details(Customer_Name,Gender,Address,Phone,DOB,Password,CreditCardNo,CreditCardType) values( ?, ?, ?, ?,?,?,?,?)";
        try {
            con1 = getConnection();
            PreparedStatement stmt = con1.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, gender);
            stmt.setString(3, address);
            stmt.setString(4, phone);
            stmt.setString(5, DOB);
            stmt.setString(6, password);
            //stmt.setString(7, email);
            stmt.setInt(7, creditcard);
            stmt.setString(8, creditCardType);

            long ent= stmt.executeUpdate();
            if(ent == -1)
                return false;
            else
                return true;

        }catch(Exception e){
            // System.out.println(e.getMessage());
            Log.e( "error here: ",e.getMessage());
            Log.e( "error here: ",e.getLocalizedMessage());
            Log.e( "error here: ",e.getCause().toString());
            return  false;
        }
    }

    public boolean insertDataEmployee(String name, String gender,
                                      String DOB,String Designation,String address,String email,String phone){
        Connection con1;
        String query = "insert into Employee_Details(ID,name,gender,DOB,Designation,address,email,phone) values(?, ?, ?, ?, ?,?,?,?)";
        try {
            con1 = getConnection();
            PreparedStatement stmt = con1.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, gender);
            stmt.setString(3, DOB);
            stmt.setString(4, Designation);
            stmt.setString(5, address);
            stmt.setString(6, email);
            stmt.setString(7, phone);


            long ent= stmt.executeUpdate();
            if(ent == -1)
                return false;
            else
                return true;

        }catch(Exception e){
            // System.out.println(e.getMessage());
            Log.e( "error here: ",e.getMessage());
            return  false;
        }
    }

    public boolean insertDataProduct(int Categoryid, String productname,
                                      int stock,String description,float price,float discount){
        Connection con1;
        String query = "insert into Product_Details(Category_ID,Product_Name,Stock,Description,Price,Discount,Image) values(?, ?, ?, ?, ?,?,?)";
        try {
            con1 = getConnection();
            PreparedStatement stmt = con1.prepareStatement(query);
            stmt.setInt(1, Categoryid);
            stmt.setString(2, productname);
            stmt.setInt(3, stock);
            stmt.setString(4, description);
            stmt.setFloat(5, price);
            stmt.setFloat(6, discount);
            //stmt.setBlob(7,discount);


            long ent= stmt.executeUpdate();
            if(ent == -1)
                return false;
            else
                return true;

        }catch(Exception e){
            // System.out.println(e.getMessage());
            Log.e( "error here: ",e.getMessage());
            return  false;
        }
    }

    public void veiwOrders()throws SQLException{
        Connection con1;
        ResultSet resultSet = null;
        String query = "select * from Order_Details";
        try {
            con1 = getConnection();
            PreparedStatement stmt = con1.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            resultSet = stmt.executeQuery();
            if(resultSet.next()){
                while(resultSet.next()){

                }
            }
        }catch(Exception e){
            //return false;
        } finally {

            if(resultSet != null)
                resultSet.close();
        }
    }

    public Boolean AddToCart(int custid, int emplid, int proid, int qty, java.sql.Date dt, double amt, String st)throws SQLException{
        Connection con1;
        ResultSet resultSet = null;
        String query = "insert into Order_Details (Customer_ID, Employee_ID, Product_ID, Quantity, Order_Date,Amount ,Status) values(?,?,?,?,?,?,?)";

        try {
            con1 = getConnection();
            PreparedStatement stmt = con1.prepareStatement(query);
            stmt.setInt(1, custid);
            stmt.setInt(2, emplid);
            stmt.setInt(3, proid);
            stmt.setInt(4, qty);
            stmt.setDate(5, dt);
            stmt.setDouble(6, amt);
            stmt.setString(7, st);

            long ent= stmt.executeUpdate();
            if(ent == -1)
                return false;
            else
                return true;
        }catch(Exception e){
            //return false;
            Log.e( "error here: ",e.getMessage());
        } finally {

            if(resultSet != null)
                resultSet.close();
            return true;
        }
    }
    int id;
    public int selectCustomerId(String name)throws SQLException{
        Connection con1;
        ResultSet resultSet = null;
        String query = "select Customer_ID from Customer_Details where Customer_Name=?";

        try {
            con1 = getConnection();
            PreparedStatement stmt = con1.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, name);

            resultSet = stmt.executeQuery();
            if(resultSet.next()){
                 this.id=resultSet.getInt("Customer_ID");
                return id;

            }
            //return id;
        }catch(Exception e){
            //return false;
            Log.e( "error here: ",e.getLocalizedMessage());
            Log.e( "error here: ",e.getMessage());
        } finally {

            if(resultSet != null)
                resultSet.close();

        }
        return id;
    }


    public boolean checkProduct(int product,int qty){
        Connection con1;
        Connection con2;
        ResultSet resultSet = null;
        String query = "select * from Order_Details where Status ='no purchase' and Product_ID=?";
        boolean bool =true;
        try {
            con1 = getConnection();
            PreparedStatement stmt = con1.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, product);
            resultSet = stmt.executeQuery();
            if(resultSet.next()){
                String query2="update Order_Details set Quantity ="+(resultSet.getInt("Quantity")+qty) +"where Product_ID=?" ;
                ResultSet resultSet1 = null;
                con2 = getConnection();
                PreparedStatement stmt1 = con2.prepareStatement(query2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                stmt1.setInt(1, product);
                stmt1.executeUpdate();
            }else {
                bool = false;
                return bool;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e( "error here1: ",e.getMessage());
            Log.e( "error here2: ",e.getLocalizedMessage());
        }
        return bool;
    }

    public void updateCustomerOrder(int id){
        Connection con2;
        String query2="update Order_Details set Status ='confirmed' where Customer_ID=?";
        try{

            con2 = getConnection();
            PreparedStatement stmt1 = con2.prepareStatement(query2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt1.setInt(1, id);
            stmt1.executeUpdate();

        }
        catch (Exception e){
            e.printStackTrace();
            Log.e( "error here1: ",e.getMessage());
            Log.e( "error here2: ",e.getLocalizedMessage());
        }
    }

    public void deleteCustomer(int id){
        Connection con2;
        String query2="delete from Customer_Details where Customer_ID=?" ;
        ResultSet resultSet1 = null;
        try{
            con2 = getConnection();
            PreparedStatement stmt1 = con2.prepareStatement(query2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt1.setInt(1, id);
            stmt1.executeUpdate();}
        catch (Exception e){

        }
    }

    public void deleteProduct(int id){
        Connection con2;
        String query2="delete from Product_Details where Product_ID=?" ;
        ResultSet resultSet1 = null;
        try{

            con2 = getConnection();
            PreparedStatement stmt1 = con2.prepareStatement(query2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt1.setInt(1, id);
            stmt1.executeUpdate();

        }
        catch (Exception e){

        }
    }

    public void deleteEmployee(int id){
        Connection con2;
        String query2="delete from Employee_Details where Employee_ID=?" ;
        ResultSet resultSet1 = null;
        try{

            con2 = getConnection();
            PreparedStatement stmt1 = con2.prepareStatement(query2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt1.setInt(1, id);
            stmt1.executeUpdate();
            
        }
        catch (Exception e){

        }
    }

    public void getCustomerDetails(int id,String name,String gender,String address,int phone,int dob,String password,int creditCard,String credittype){
        Connection con2;
        String query2="delete from Employee_Details where Employee_ID=?" ;
        ResultSet resultSet1 = null;
        try{

            con2 = getConnection();
            PreparedStatement stmt1 = con2.prepareStatement(query2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt1.setInt(1, id);
            ResultSet resultSet=stmt1.executeQuery();
            while(resultSet.next()){
                name =resultSet.getString("Customer_Name");
                gender =resultSet.getString("Gender");
                address =resultSet.getString("Address");
                phone =resultSet.getInt("Phone");
                dob =resultSet.getInt("DOB");
                password =resultSet.getString("Password");
                creditCard =resultSet.getInt("CreditCardNo");
                credittype =resultSet.getString("CreditCardType");
            }

        }
        catch (Exception e){

        }
    }

    public void getProductDetails(int id, int catid, String name, int stock, String desc, float price, float discount,InputStream is){
        Connection con2;
        String query2="select * from Product_Details where Product_ID=?" ;
        ResultSet resultSet1 = null;
        try{

            con2 = getConnection();
            PreparedStatement stmt1 = con2.prepareStatement(query2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt1.setInt(1, id);
            ResultSet resultSet=stmt1.executeQuery();
            while(resultSet.next()){
                catid =resultSet.getInt("Category_ID");
                name =resultSet.getString("Product_Name");
                stock =resultSet.getInt("Stock");
                desc =resultSet.getString("Description");
                price =resultSet.getFloat("Price");
                discount =resultSet.getFloat("Category_ID");
                is =resultSet.getBinaryStream("Image");
            }

        }
        catch (Exception e){

        }
    }

    public void deleteItemFromCart(int custid,int proid){
        Connection con2;
        String query2="delete from Order_Details where Customer_ID=? and Product_ID=?" ;
        try{
            con2 = getConnection();
            PreparedStatement stmt1 = con2.prepareStatement(query2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt1.setInt(1, custid);
            stmt1.setInt(2, proid);
            stmt1.execute();
        }
        catch (Exception e){

        }
    }

    public void getEmployeeDetails(int id,String name,String gender,int dob,String desig,String Address,String email,String password,int phone){
        Connection con2;
        String query2="delete from Employee_Details where Employee_ID=?" ;
        ResultSet resultSet1 = null;
        try{

            con2 = getConnection();
            PreparedStatement stmt1 = con2.prepareStatement(query2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt1.setInt(1, id);
            ResultSet resultSet=stmt1.executeQuery();
            while(resultSet.next()){
                name =resultSet.getString("Employee_Name");
                gender =resultSet.getString("Gender");
                dob =resultSet.getInt("DOB");
                desig =resultSet.getString("Designation");
                Address =resultSet.getString("Address");
                email =resultSet.getString("Email");
                phone =resultSet.getInt("Phone");
            }

        }
        catch (Exception e){

        }
    }

    public Boolean updateEmployee(int id,String name,String gender,String dob,String designation,String address,String email,String phone,String password){
        Connection con2;
        String query2="update Employee_Details set Employee_Name = ?,Gender=?,DOB=?,Designation=?,Address=?,Email=?,Phone=?,Password=? where Employee_ID=?" ;
        ResultSet resultSet1 = null;
        try{

            con2 = getConnection();
            PreparedStatement stmt1 = con2.prepareStatement(query2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            stmt1.setInt(1, id);
            stmt1.setString(2, name);
            stmt1.setString(3, gender);
            stmt1.setString(4, dob);
            stmt1.setString(5, designation);
            stmt1.setString(6, address);
            stmt1.setString(7, email);
            stmt1.setString(8, phone);
            stmt1.setString(9, password);

            long ent= stmt1.executeUpdate();
            if(ent == -1)
                return false;
            else
                return true;

        }
        catch (Exception e){
            return true;

        }
    }

    public Boolean updateCustomer(int id,String name,String gender,String address,String phone,String dob,String password,int creditcard,int creditcardtype){
        Connection con2;
        String query2="update Customer_Details set Customer_Name = ?,Gender=?,Address=?,Phone=?,DOB=?,Password=?,CreditCardNo=?,CreditCardType=? where Customer_ID=?" ;
        ResultSet resultSet1 = null;
        try{

            con2 = getConnection();
            PreparedStatement stmt1 = con2.prepareStatement(query2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            stmt1.setInt(1, id);
            stmt1.setString(2, name);
            stmt1.setString(3, gender);
            stmt1.setString(4, address);
            stmt1.setString(5, phone);
            stmt1.setString(6, dob);
            stmt1.setString(7, password);
            stmt1.setInt(8, creditcard);
            stmt1.setInt(9, creditcardtype);

            long ent= stmt1.executeUpdate();
            if(ent == -1)
                return false;
            else
                return true;

        }
        catch (Exception e){
            return true;
        }
    }

    public Boolean updateProduct(int id,String name,int stock,String description,float price,float discount){
        Connection con2;
        String query2="update Product_Details set Category_ID = ?,Product_Name=?,Stock=?,Description=?,Price=?,Discount=?,Image=? where Product_ID=?" ;
        ResultSet resultSet1 = null;
        try{

            con2 = getConnection();
            PreparedStatement stmt1 = con2.prepareStatement(query2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            stmt1.setInt(1, id);
            stmt1.setString(2, name);
            stmt1.setInt(3, stock);
            stmt1.setString(4, description);
            stmt1.setFloat(5, price);
            stmt1.setFloat(6, discount);

            long ent= stmt1.executeUpdate();
            if(ent == -1)
                return false;
            else
                return true;

        }
        catch (Exception e){
            return true;
        }
    }

    public StringBuilder listCustomerProducts(int id){
        Connection con2;
        String query2="select * from Order_Details where Status ='no purchase' and Customer_ID =?" ;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        StringBuilder prod = new StringBuilder();
        try{
            con2 = getConnection();
            PreparedStatement stmt1 = con2.prepareStatement(query2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt1.setInt(1, id);
            resultSet1 = stmt1.executeQuery();

            while(resultSet1.next()){
                int prody = resultSet1.getInt("Product_ID");
                String query3 ="select DISTINCT Product_Name from Product_Details where Product_ID="+prody;
                try {
                    PreparedStatement stmt2 = con2.prepareStatement(query3, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    resultSet2 = stmt2.executeQuery();
                    String prodd = "";
                    while (resultSet2.next()) {
                        prodd = resultSet2.getString("Product_Name");
                        prod.append(prodd + ",");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.e( "error here1: ",e.getMessage());
                    Log.e( "error here2: ",e.getLocalizedMessage());
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
            Log.e( "error here3: ",e.getMessage());
            Log.e( "error here4: ",e.getLocalizedMessage());
        }
        return prod;
    }
}
