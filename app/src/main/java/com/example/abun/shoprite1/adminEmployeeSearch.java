package com.example.abun.shoprite1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class adminEmployeeSearch extends AppCompatActivity {

    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<employeeTable> data1;
    ConnectionClass db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_employee_search);
        recyclerView =(RecyclerView) findViewById(R.id.displayRecycler11);
        recyclerView.setHasFixedSize(true);
        data1 = new ArrayList<employeeTable>();
        mLayoutManager = new LinearLayoutManager(adminEmployeeSearch.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        db = new ConnectionClass();
        SyncData syd = new SyncData();
        syd.execute("");
    }

    private class SyncData extends AsyncTask<String ,String,String> {
        String msg="Internet/DB_Credentials/Windows_FireWall_TurnOn Error, see android monitor in the buttom for details";
        ProgressDialog progress;
        boolean success = false;

        @Override
        protected void onPreExecute(){
            progress = ProgressDialog.show(adminEmployeeSearch.this,"Synchronising","RecyclerView loading! please wait...",true);
        }

        @Override
        protected String doInBackground(String...strings){
            try {
                Connection con = db.getConnection();
                ResultSet resultSet = null;

                if (con == null) {
                    msg="No Data found";
                    success = false;
                }else{
                    String query = "SELECT * FROM Employee_Details ";
                    PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    resultSet = stmt.executeQuery();

                    while (resultSet.next() ) {
                        try {

                            data1.add(new employeeTable(resultSet.getInt("Employee_ID"),resultSet.getString("Employee_Name"), resultSet.getString("Designation"), resultSet.getString("Gender"),resultSet.getString("Address"), resultSet.getString("Email"), resultSet.getString("Phone"), resultSet.getDate("DOB")));

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
            Toast.makeText(adminEmployeeSearch.this,msg+"",Toast.LENGTH_LONG).show();
            if(success == true){

                mAdapter = new adminAdapterE(data1,adminEmployeeSearch.this);
                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);

            }else{
                try{

                }catch (Exception e){

                }

            }
        }
    }
}
