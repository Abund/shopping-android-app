package com.example.abun.shoprite1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import android.view.View;

public class EmployeeConfirmationPage extends AppCompatActivity {
    ConnectionClass db;
    String name;
    private ArrayList<OrderTable> data;
    private TextView customername;
    private Button confirmo;
    private Button logout;
    private TextView listings;
    StringBuilder bul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_confirmation_page);

        customername = (TextView) findViewById(R.id.cusname);
        listings = (TextView) findViewById(R.id.listpro);
        confirmo = (Button) findViewById(R.id.confirmo);
        logout = (Button) findViewById(R.id.logout2);

        Intent intent = getIntent();
        name = intent.getExtras().getString("name111");
        customername.setText(name);
        StringBuilder prode= new StringBuilder();
        ConnectionClass cont = new ConnectionClass();
        try {
            int namy = cont.selectCustomerId(name);
            prode = cont.listCustomerProducts(namy);
        }catch (Exception ex){

        }
        listings.setText(prode);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeConfirmationPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
        confirmo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ConnectionClass cont1 = new ConnectionClass();
                    int id = cont1.selectCustomerId(name);
                    cont1.updateCustomerOrder(id);
                    Intent intent = new Intent(EmployeeConfirmationPage.this, EmployeePage.class);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                    Log.e( "error here1: ",e.getMessage());
                    Log.e( "error here2: ",e.getLocalizedMessage());
                }
            }
        });
    }

    private class SyncData extends AsyncTask<String ,String,String> {
        String msg="Internet/DB_Credentials/Windows_FireWall_TurnOn Error, see android monitor in the buttom for details";
        ProgressDialog progress;
        boolean success = false;

        @Override
        protected void onPreExecute(){
            progress = ProgressDialog.show(EmployeeConfirmationPage.this,"Synchronising","RecyclerView loading! please wait...",true);
        }

        @Override
        protected String doInBackground(String...strings){
            try {
                Connection con = db.getConnection();

                ResultSet resultSet = null;
                ResultSet resultSet1 = null;

                if (con == null) {

                    msg="No Data found";
                    success = false;
                }else{
                    ConnectionClass cont = new ConnectionClass();
                    int custid=cont.selectCustomerId(name);
                    String query = "SELECT * FROM Order_Details where Status ='no purchase' and Customer_ID ="+custid;
                    PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    resultSet = stmt.executeQuery();

                    while (resultSet.next() ) {
                        try {

                            data.add(new OrderTable(resultSet.getInt("Order_ID"), resultSet.getInt("Customer_ID"), resultSet.getInt("Employee_ID"),resultSet.getInt("Product_ID"), resultSet.getInt("Quantity"), resultSet.getDouble("Amount")));

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
            Toast.makeText(EmployeeConfirmationPage.this,msg+"",Toast.LENGTH_LONG).show();
            if(success == true){

                for(int i =0;i<=data.size();i++){
                    bul.append(data.get(1)+",");
                }
                listings.setText(bul);

            }else{
                try{

                }catch (Exception e){

                }

            }
        }
    }
}
