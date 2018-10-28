package com.example.abun.shoprite1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProductCatergory extends AppCompatActivity {

    private mAdapt mAdapter;
    ConnectionClass db;
    private GridView gridView;
    private ArrayList<ProductPageRetrive> data;
    private boolean success=false;
    Intent intent = getIntent();
    String cat ;
    int cat2;
    int user2;
    private TextView topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catergory);
        db= new ConnectionClass();
        data = new ArrayList<ProductPageRetrive>();
        this. cat = getIntent().getStringExtra("coding");

        gridView =(GridView) findViewById(R.id.gridView);
        topic =(TextView) findViewById(R.id.topic);
        int caty = Integer.parseInt(cat);
        if(caty == 1){
            topic.setText("BEVERAGES");
        }else if(caty == 2){
            topic.setText("PERSONAL CARE");
        }else if(caty == 3){
            topic.setText("HOUSEHOLD PRODUCTS");
        }else if(caty == 4){
            topic.setText("ELECTRONICS");
        }else if(caty == 5){
            topic.setText("GROCERIES");
        }


        /*this.cat = intent.getStringExtra("cat");
        this.num = Integer.parseInt(cat);*/

        SyncData orderData = new SyncData();
        orderData.execute("");


    }

    public void GetUser(int user){

        this.user2=user;
    }

    private class SyncData extends AsyncTask<String ,String,String> {
        String msg="Internet/DB_Credentials/Windows_FireWall_TurnOn Error, see android monitor in the buttom for details";
        ProgressDialog progress;
        boolean success = false;

        @Override
        protected void onPreExecute(){
            progress = ProgressDialog.show(ProductCatergory.this,"Synchronising","RecyclerView loading! please wait...",true);
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
                    String query = "SELECT * FROM Product_Details where Category_ID =" + cat ;
                    PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    resultSet = stmt.executeQuery();


                    while (resultSet.next()) {
                        try {

                            data.add(new ProductPageRetrive(resultSet.getBinaryStream("Image"),resultSet.getInt("Product_ID"), resultSet.getString("Product_Name"), resultSet.getInt("Price"),resultSet.getInt("Stock"), resultSet.getString("Description"), resultSet.getInt("Discount")));

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
            Toast.makeText(ProductCatergory.this,msg+"",Toast.LENGTH_LONG).show();
            if(success == true){

                mAdapter = new mAdapt(data,ProductCatergory.this);
                gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
                gridView.setAdapter(mAdapter);

            }else{
                try{

                }catch (Exception e){

                }

            }
        }
    }
}
