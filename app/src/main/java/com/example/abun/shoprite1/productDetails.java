package com.example.abun.shoprite1;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.widget.Toast;
import android.app.Fragment;
import android.util.Log;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class productDetails extends AppCompatActivity implements
        FragmentToActivity{
    private ImageView imageButton;
    private ImageView imageView;
    private TextView ProductName;
    private int quantity=1;
    private TextView Rating;
    private Button Buy,add,sub;
    private TextView ProductDescription;
    private TextView ProductNote;
    private TextView productreview;
    private EditText WriteReview;
    private RecyclerView Productslikes;
    private RecyclerView Recommended;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager1;
    private RecyclerView.Adapter mAdapter1;
    private ArrayList<ProductPageRetrive> data;
    private ArrayList<ProductPageRetrive> data1;
    FloatingActionButton floaty;
    ConnectionClass db;
    private TextView quantityView;
    UserSessionManager session;
     String name1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);
        db = new ConnectionClass();
        //floaty.setVisibility(imageView.INVISIBLE);


        session = new UserSessionManager(getApplicationContext());
        quantityView = (TextView) findViewById(R.id.quantityProductPage);
        quantityView.setText(String.valueOf(quantity));

        add = (Button) findViewById(R.id.incrementQuantity);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment();
            }
        });

        sub = (Button) findViewById(R.id.decrementQuantity);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrement();
            }
        });


        if(session.checkLogin())
            finish();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get name
        this.name1 = user.get(UserSessionManager.KEY_NAME);

        // get email
        String email = user.get(UserSessionManager.KEY_EMAIL);

        SyncData orderData = new SyncData();
        orderData.execute("");

        Intent intent = getIntent();
        imageButton = (ImageView) findViewById(R.id.imageButton);
        imageView = (ImageView) findViewById(R.id.imageProduct);
        ProductName = (TextView) findViewById(R.id.productName);
        Rating = (TextView) findViewById(R.id.rating);
        ProductName.setText(name1);
        Buy = (Button) findViewById(R.id.buy);
        ProductDescription = (TextView) findViewById(R.id.prodDiscription);
        ProductNote = (TextView) findViewById(R.id.proNote);
        //WriteReview = (EditText) findViewById(R.id.writeReview);
        Productslikes = (RecyclerView) findViewById(R.id.productslikes);
        Recommended = (RecyclerView) findViewById(R.id.recommendation);
        floaty = (FloatingActionButton) findViewById(R.id.cartProductPage);
        productreview = (TextView) findViewById(R.id.review101);
        productreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert();
            }
        });
        floaty.hide();

        final int  proid = intent.getExtras().getInt("proid");
        Bitmap bit = (Bitmap) intent.getParcelableExtra("bitmap");
        String name = intent.getExtras().getString("name");
        String Description = intent.getExtras().getString("Description");
        final double price = intent.getExtras().getInt("Price")*quantity;
        int Stock = intent.getExtras().getInt("Stock");


        Buy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    ConnectionClass cont= new ConnectionClass();
                    boolean bool = cont.checkProduct(proid,quantity);
                    if (bool==false){
                        String noPurchase="no purchase";
                        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                        int usid= cont.selectCustomerId(name1);
                        int empid=1;
                        if(cont.AddToCart(usid,empid,proid,quantity,date,price,noPurchase)){
                            floaty.show();
                        }else{
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error occurred",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }
                catch (Exception e){

                }
            }
        });

        floaty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.gg, new Cart()).commit();
            }
        });

        data = new ArrayList<ProductPageRetrive>();
        data1 = new ArrayList<ProductPageRetrive>();

        Productslikes.setHasFixedSize(true);
        Recommended.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(productDetails.this,LinearLayoutManager.HORIZONTAL,false);
        mLayoutManager1 = new LinearLayoutManager(productDetails.this,LinearLayoutManager.HORIZONTAL,false);

        Productslikes.setLayoutManager(mLayoutManager);
        Recommended.setLayoutManager(mLayoutManager1);


        imageButton.setImageBitmap(bit);
        imageView.setImageBitmap(bit);
        ProductName.setText(name);
        Rating.setText(price+"");
        ProductNote.setText(Description);




    }

    @Override
    public void communicate(int s) {
       // Log.d("received", s);
    }

    void increment(){
        if (quantity < 5){
            quantity++;
            quantityView.setText(String.valueOf(quantity));
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Limit of 5 products only",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    void decrement(){
        if (quantity > 1){
            quantity--;
            quantityView.setText(String.valueOf(quantity));
        }
    }

    private class SyncData extends AsyncTask<String ,String,String> {
        String msg="Internet/DB_Credentials/Windows_FireWall_TurnOn Error, see android monitor in the buttom for details";
        ProgressDialog progress;
        boolean success = false;

        @Override
        protected void onPreExecute(){
           progress = ProgressDialog.show(productDetails.this,"Synchronising","RecyclerView loading! please wait...",true);
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
                    String query = "SELECT * FROM Product_Details where Category_ID = 2 ";
                    PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    String query1 = "SELECT * FROM Product_Details where Category_ID = 4 ";
                    PreparedStatement stmt1 = con.prepareStatement(query1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


                    resultSet = stmt.executeQuery();
                    resultSet1 = stmt1.executeQuery();


                    while (resultSet.next()||resultSet1.next() ) {
                        try {

                            data.add(new ProductPageRetrive(resultSet.getBinaryStream("Image"),resultSet.getInt("Product_ID"), resultSet.getString("Product_Name"), resultSet.getInt("Price"),resultSet.getInt("Stock"), resultSet.getString("Description"), resultSet.getInt("Discount")));

                        }catch(Exception ex){
                            ex.printStackTrace();
                        }

                        try {

                            data1.add(new ProductPageRetrive(resultSet1.getBinaryStream("Image"),resultSet.getInt("Product_ID"), resultSet1.getString("Product_Name"), resultSet1.getInt("Price"),resultSet1.getInt("Stock"), resultSet1.getString("Description"), resultSet1.getInt("Discount")));

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
            Toast.makeText(productDetails.this,msg+"",Toast.LENGTH_LONG).show();
            if(success == true){

                mAdapter = new MainAdapter(data,productDetails.this);
                mAdapter.notifyDataSetChanged();
                Productslikes.setAdapter(mAdapter);

                mAdapter1 = new MainAdapter(data1,productDetails.this);
                mAdapter1.notifyDataSetChanged();
                Recommended.setAdapter(mAdapter1);



            }else{
                try{

                }catch (Exception e){

                }

            }
        }
    }
    public void alert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(productDetails.this);
        WriteReview = new EditText(productDetails.this);
        alert.setTitle("Write review");
        alert.setView(WriteReview);
        alert.setPositiveButton("Submit",new OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which){

            }
        });
        alert.setNegativeButton("Cancel",new OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which){

            }
        });
        alert.show();
    }
}
