package com.example.abun.shoprite1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.app.TabActivity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EmployeePage extends AppCompatActivity {
    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    public ArrayList<orderModified> data = new ArrayList<orderModified>();
    public ArrayList<customerTable> data1= new ArrayList<customerTable>();

    private RecyclerView mRecycler1;
    private RecyclerView.LayoutManager mLayoutManager1;
    private RecyclerView.Adapter mAdapter1;
    public ArrayList<orderModified> data2= new ArrayList<orderModified>();
    public ArrayList<customerTable> data3= new ArrayList<customerTable>();
    ConnectionClass db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page);

        mRecycler = (RecyclerView) findViewById(R.id.customerOrders);
        mRecycler1 = (RecyclerView) findViewById(R.id.customerOrders1);

        TabHost tabHost = (TabHost)findViewById(R.id.tabhost);
        tabHost.setup();

        TabSpec placeOrders = tabHost.newTabSpec("placedOrders");
        placeOrders.setContent(R.id.placedOrders);
        placeOrders.setIndicator("Placed Orders");
        tabHost.addTab(placeOrders);

        TabSpec confirmedOrders = tabHost.newTabSpec("confirmedOrders");
        confirmedOrders.setContent(R.id.confirmedOrders);
        confirmedOrders.setIndicator("Confirmed Orders");
        tabHost.addTab(confirmedOrders);

        db = new ConnectionClass();
        mRecycler.setHasFixedSize(true);
        mRecycler1.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(EmployeePage.this,LinearLayoutManager.VERTICAL,false);
        mRecycler.setLayoutManager(mLayoutManager);

        mLayoutManager1 = new LinearLayoutManager(EmployeePage.this,LinearLayoutManager.VERTICAL,false);
        mRecycler1.setLayoutManager(mLayoutManager1);

        SyncData dy = new SyncData();
        dy.execute("");
    }

    private class SyncData extends AsyncTask<String ,String,String> {
        String msg="Internet/DB_Credentials/Windows_FireWall_TurnOn Error, see android monitor in the buttom for details";
        ProgressDialog progress;
        boolean success = false;

        @Override
        protected void onPreExecute(){
            progress = ProgressDialog.show(EmployeePage.this,"Synchronising","RecyclerView loading! please wait...",true);
        }

        @Override
        protected String doInBackground(String...strings){
            try {
                Connection con = db.getConnection();
                Connection con1 = db.getConnection();
                Connection con3 = db.getConnection();

                ResultSet resultSet = null;
                ResultSet resultSet1 = null;

                ResultSet resultSet2 = null;
                ResultSet resultSet3 = null;

                if (con == null) {

                    msg="No Data found";
                    success = false;
                }else{
                    String query = "select count(Quantity) as Quantity,Customer_ID from Order_Details where Status='no purchase' group by Customer_ID ";
                    PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    resultSet = stmt.executeQuery();

                    while (resultSet.next()) {
                        try {
                            int customerid = resultSet.getInt("Customer_ID");
                            //data.add(new customerTable(resultSet.getInt("Customer_ID"),resultSet.getString("Customer_Name"), resultSet.getString("Gender"), resultSet.getString("Address"),resultSet.getString("Phone"), resultSet.getDate("DOB"), resultSet.getString("Password"), resultSet.getInt("CreditCardNo"), resultSet.getInt("CreditCardType")));
                            data.add(new orderModified(resultSet.getInt("Customer_ID"),resultSet.getInt("Quantity")));

                            String query1 = "SELECT * FROM Customer_Details where Customer_ID ="+customerid;
                            PreparedStatement stmt1 = con1.prepareStatement(query1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            resultSet1 = stmt1.executeQuery();
                            while (resultSet1.next()) {
                                try {
                                    data1.add(new customerTable(resultSet1.getInt("Customer_ID"),resultSet1.getString("Customer_Name"), resultSet1.getString("Gender"), resultSet1.getString("Address"),resultSet1.getString("Phone"), resultSet1.getDate("DOB"), resultSet1.getString("Password"), resultSet1.getInt("CreditCardNo"), resultSet1.getString("CreditCardType")));
                                 }catch(Exception ex){
                                    ex.printStackTrace();
                                    Log.e( "error here1: ",ex.getMessage());
                                    Log.e( "error here2: ",ex.getLocalizedMessage());
                                }
                            }
                        }catch(Exception ex){
                            ex.printStackTrace();
                            Log.e( "error here3: ",ex.getCause().toString());
                            Log.e( "error here4: ",ex.getLocalizedMessage());
                        }

                    }

                    String query11 = "select count(Quantity) as Quantity,Customer_ID from Order_Details where Status='confirmed' group by Customer_ID ";
                    PreparedStatement stmt11 = con.prepareStatement(query11, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    resultSet2 = stmt11.executeQuery();

                    while (resultSet2.next() ) {
                        try {
                            int customerid = resultSet2.getInt("Customer_ID");
                            //data.add(new customerTable(resultSet.getInt("Customer_ID"),resultSet.getString("Customer_Name"), resultSet.getString("Gender"), resultSet.getString("Address"),resultSet.getString("Phone"), resultSet.getDate("DOB"), resultSet.getString("Password"), resultSet.getInt("CreditCardNo"), resultSet.getInt("CreditCardType")));
                            data2.add(new orderModified(resultSet2.getInt("Customer_ID"),resultSet2.getInt("Quantity")));

                            String query12 = "SELECT * FROM Customer_Details where Customer_ID ="+customerid;
                            PreparedStatement stmt12 = con1.prepareStatement(query12, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            resultSet3 = stmt12.executeQuery();
                            while (resultSet3.next()  ) {

                                try {
                                    data3.add(new customerTable(resultSet3.getInt("Customer_ID"),resultSet3.getString("Customer_Name"), resultSet3.getString("Gender"), resultSet3.getString("Address"),resultSet3.getString("Phone"), resultSet3.getDate("DOB"), resultSet3.getString("Password"), resultSet3.getInt("CreditCardNo"), resultSet3.getString("CreditCardType")));

                                }catch(Exception ex){
                                    ex.printStackTrace();
                                    Log.e( "error here2: ",ex.getMessage());
                                    Log.e( "error here2: ",ex.getLocalizedMessage());
                                }
                            }
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }

                    }

                    msg ="found";
                    success=true;
                }
            }catch(Exception e){
                e.printStackTrace();
                //Write write = new StringWriter();
                //e.printStackTrace(new PrintWriter(write));
                //msg=writer.toString();
                success=false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg){
            progress.dismiss();
            Toast.makeText(EmployeePage.this,msg+"",Toast.LENGTH_LONG).show();
            if(success == true){

                mAdapter = new EmployeeOrderAdapter(data,data1,EmployeePage.this);
                mAdapter.notifyDataSetChanged();
                mRecycler.setAdapter(mAdapter);

                mAdapter1 = new EmployeeOrderAdapter(data2,data3,EmployeePage.this);
                mAdapter1.notifyDataSetChanged();
                mRecycler1.setAdapter(mAdapter1);

            }else{
                try{

                }catch (Exception e){

                }

            }
        }
    }
}
