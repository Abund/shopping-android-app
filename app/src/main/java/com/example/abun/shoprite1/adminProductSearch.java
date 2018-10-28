package com.example.abun.shoprite1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class adminProductSearch extends AppCompatActivity {

    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<ProductPageRetrive> data1;
    ConnectionClass db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_search);
        recyclerView =(RecyclerView) findViewById(R.id.displayRecycler111);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(adminProductSearch.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        data1 = new ArrayList<ProductPageRetrive>();
        db = new ConnectionClass();

        SyncData dy = new SyncData();
        dy.execute("");
    }

    private class SyncData extends AsyncTask<String ,String,String> {
        String msg="Internet/DB_Credentials/Windows_FireWall_TurnOn Error, see android monitor in the buttom for details";
        ProgressDialog progress;
        boolean success = false;

        @Override
        protected void onPreExecute(){
            progress = ProgressDialog.show(adminProductSearch.this,"Synchronising","RecyclerView loading! please wait...",true);
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
                    String query = "SELECT * FROM Product_Details ";
                    PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    resultSet = stmt.executeQuery();

                    while (resultSet.next() ) {
                        try {

                            data1.add(new ProductPageRetrive(resultSet.getBinaryStream("Image"),resultSet.getInt("Product_ID"), resultSet.getString("Product_Name"), resultSet.getInt("Price"),resultSet.getInt("Stock"), resultSet.getString("Description"), resultSet.getInt("Discount")));
                        }catch(Exception ex){
                            ex.printStackTrace();
                            Log.e( "error here3: ",ex.getMessage());
                            Log.e( "error here4: ",ex.getLocalizedMessage());
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
            Toast.makeText(adminProductSearch.this,msg+"",Toast.LENGTH_LONG).show();
            if(success == true){

                mAdapter = new adminAdapterP(data1,adminProductSearch.this);
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
