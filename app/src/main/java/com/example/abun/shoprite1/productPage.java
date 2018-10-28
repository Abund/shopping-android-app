package com.example.abun.shoprite1;

/**
 * Created by Abun on 5/12/2018.
 */

import android.app.Activity;
import java.util.HashMap;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.support.annotation.RequiresPermission.Write;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.content.Intent;
//import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.support.v7.app.ActionBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class productPage extends AppCompatActivity /*implements NavigationView.OnNavigationItemSelectedListener*/  {

    private RecyclerView mRecycler;
    private RecyclerView mRecycler1;
    private RecyclerView mRecycler2;
    private RecyclerView mRecycler3;

    private TextView more1;
    private TextView more2;
    private TextView more3;
    private TextView more4;

    private TextView user1;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager1;
    private RecyclerView.LayoutManager mLayoutManager2;
    private RecyclerView.LayoutManager mLayoutManager3;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.Adapter mAdapter2;
    private RecyclerView.Adapter mAdapter3;

    private DrawerLayout mDrawerLayout;
    private ArrayList<String> mData;

    private ArrayList<ProductPageRetrive> data;
    private ArrayList<ProductPageRetrive> data1;
    private ArrayList<ProductPageRetrive> data2;
    private ArrayList<ProductPageRetrive> data3;

    ConnectionClass db;
    UserSessionManager session;
    String name1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productpage1);
        db = new ConnectionClass();
        session = new UserSessionManager(getApplicationContext());

        if(session.checkLogin())
            finish();

        // get user data from session
        final HashMap<String, String> user = session.getUserDetails();

        // get name
        this.name1 = user.get(UserSessionManager.KEY_NAME);

        // get email
        String email = user.get(UserSessionManager.KEY_EMAIL);

        data = new ArrayList<ProductPageRetrive>();
        data1 = new ArrayList<ProductPageRetrive>();
        data2 = new ArrayList<ProductPageRetrive>();
        data3 = new ArrayList<ProductPageRetrive>();

        mData = new ArrayList<>();
        for (int i = 0;i<30;i++){
            mData.add("new title"+i);
        }

        user1 = (TextView) findViewById(R.id.user11);
//        user1.setText(name1);

        SyncData orderData = new SyncData();
        orderData.execute("");

        mRecycler = (RecyclerView) findViewById(R.id.recycler_view);
        mRecycler1 = (RecyclerView) findViewById(R.id.recycler_view1);
        mRecycler2 = (RecyclerView) findViewById(R.id.recycler_view2);
        mRecycler3 = (RecyclerView) findViewById(R.id.recycler_view3);


        more1 = (TextView) findViewById(R.id.more1);
        more2 = (TextView) findViewById(R.id.more2);
        more3 = (TextView) findViewById(R.id.more3);
        more4 = (TextView) findViewById(R.id.more4);

        more1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(productPage.this, ProductCatergory.class);
                at.putExtra("coding","2");
                startActivity(at);
            }
        });
        more2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(productPage.this, ProductCatergory.class);
                at.putExtra("coding","4");
                startActivity(at);
            }
        });
        more3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(productPage.this, ProductCatergory.class);
                at.putExtra("coding","1");
                startActivity(at);
            }
        });
        more4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(productPage.this, ProductCatergory.class);
                at.putExtra("coding","3");
                startActivity(at);
            }
        });

        mRecycler.setHasFixedSize(true);
        mRecycler1.setHasFixedSize(true);
        mRecycler2.setHasFixedSize(true);
        mRecycler3.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(productPage.this,LinearLayoutManager.HORIZONTAL,false);
        mLayoutManager1 = new LinearLayoutManager(productPage.this,LinearLayoutManager.HORIZONTAL,false);
        mLayoutManager2 = new LinearLayoutManager(productPage.this,LinearLayoutManager.HORIZONTAL,false);
        mLayoutManager3 = new LinearLayoutManager(productPage.this,LinearLayoutManager.HORIZONTAL,false);

        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler1.setLayoutManager(mLayoutManager1);
        mRecycler2.setLayoutManager(mLayoutManager2);
        mRecycler3.setLayoutManager(mLayoutManager3);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.menuicon);
        //actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        int id = menuItem.getItemId();

                        FragmentManager fragmentManager = getFragmentManager();
                        if (id == R.id.nav_home) {
                            // Handle the camera action
                            Intent at = new Intent(productPage.this, productPage.class);
                            startActivity(at);
                        } else if (id == R.id.category) {
                            fragmentManager.beginTransaction().replace(R.id.content_frame, new category()).commit();

                        } else if (id == R.id.cart) {
                            fragmentManager.beginTransaction().replace(R.id.content_frame, new Cart()).commit();

                        }else if (id == R.id.account) {
                            session.logoutUser();
                            //Intent at = new Intent(productPage.this, MainActivity.class);
                            //startActivity(at);
                        }
                        return true;
                    }
                });
    }

    public void GetUser(int num){
        ProductCatergory dp = new ProductCatergory();
        dp.GetUser(num);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent at = new Intent(productPage.this, productPage.class);
            startActivity(at);

        } else if (id == R.id.category) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new category()).commit();

        } else if (id == R.id.cart) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Cart()).commit();

        }else if (id == R.id.account) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Cart()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/


    private class SyncData extends AsyncTask<String ,String,String>{
        String msg="Internet/DB_Credentials/Windows_FireWall_TurnOn Error, see android monitor in the buttom for details";
        ProgressDialog progress;
        boolean success = false;

        @Override
        protected void onPreExecute(){
            progress = ProgressDialog.show(productPage.this,"Synchronising","RecyclerView loading! please wait...",true);
        }

        @Override
        protected String doInBackground(String...strings){
            try {
                Connection con = db.getConnection();
                Connection con1 = db.getConnection();
                Connection con2 = db.getConnection();
                Connection con3 = db.getConnection();

                ResultSet resultSet = null;
                ResultSet resultSet1 = null;
                ResultSet resultSet2 = null;
                ResultSet resultSet3 = null;
                if (con == null) {

                    msg="No Data found";
                    success = false;
                }else{
                    String query = "SELECT * FROM Product_Details where Category_ID = 2 ";
                    PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    String query1 = "SELECT * FROM Product_Details where Category_ID = 4 ";
                    PreparedStatement stmt1 = con1.prepareStatement(query1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    String query2 = "SELECT * FROM Product_Details where Category_ID = 1 ";
                    PreparedStatement stmt2 = con2.prepareStatement(query2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    String query3 = "SELECT * FROM Product_Details where Category_ID = 3 ";
                    PreparedStatement stmt3 = con3.prepareStatement(query3, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


                    resultSet = stmt.executeQuery();
                    resultSet1 = stmt1.executeQuery();
                    resultSet2 = stmt2.executeQuery();
                    resultSet3 = stmt3.executeQuery();

                    while (resultSet.next() ||resultSet1.next()||resultSet2.next()||resultSet3.next()) {
                        try {

                            data.add(new ProductPageRetrive(resultSet.getBinaryStream("Image"),resultSet.getInt("Product_ID"), resultSet.getString("Product_Name"), resultSet.getInt("Price"),resultSet.getInt("Stock"), resultSet.getString("Description"), resultSet.getInt("Discount")));

                        }catch(Exception ex){
                            ex.printStackTrace();
                            Log.e( "error here: ",ex.getLocalizedMessage());
                            Log.e( "error here: ",ex.getMessage());
                        }
                        try {
                            data1.add(new ProductPageRetrive(resultSet1.getBinaryStream("Image"),resultSet1.getInt("Product_ID"), resultSet1.getString("Product_Name"), resultSet1.getInt("Price"),resultSet1.getInt("Stock"), resultSet1.getString("Description"), resultSet1.getInt("Discount")));

                        }catch(Exception ex){
                            ex.printStackTrace();
                            Log.e( "error here: ",ex.getLocalizedMessage());
                            Log.e( "error here: ",ex.getMessage());
                        }

                        try {
                            data2.add(new ProductPageRetrive(resultSet2.getBinaryStream("Image"),resultSet2.getInt("Product_ID"), resultSet2.getString("Product_Name"), resultSet2.getInt("Price"),resultSet2.getInt("Stock"), resultSet2.getString("Description"), resultSet2.getInt("Discount")));

                        }catch(Exception ex){
                            ex.printStackTrace();
                            Log.e( "error here: ",ex.getLocalizedMessage());
                            Log.e( "error here: ",ex.getMessage());
                        }
                        try {
                            data3.add(new ProductPageRetrive(resultSet3.getBinaryStream("Image"),resultSet3.getInt("Product_ID"), resultSet3.getString("Product_Name"), resultSet3.getInt("Price"),resultSet3.getInt("Stock"), resultSet3.getString("Description"), resultSet3.getInt("Discount")));

                        }catch(Exception ex){
                            ex.printStackTrace();
                            Log.e( "error here: ",ex.getLocalizedMessage());
                            Log.e( "error here: ",ex.getMessage());
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
                Log.e( "error here: ",e.getLocalizedMessage());
                Log.e( "error here: ",e.getMessage());
                success=false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg){
            progress.dismiss();
            Toast.makeText(productPage.this,msg+"",Toast.LENGTH_LONG).show();
            if(success == true){

                mAdapter = new MainAdapter(data,productPage.this);
                mAdapter.notifyDataSetChanged();
                mRecycler.setAdapter(mAdapter);

                mAdapter1 = new MainAdapter(data1,productPage.this);
                mAdapter1.notifyDataSetChanged();
                mRecycler1.setAdapter(mAdapter1);

                mAdapter2 = new MainAdapter(data2,productPage.this);
                mAdapter2.notifyDataSetChanged();
                mRecycler2.setAdapter(mAdapter2);

                mAdapter3 = new MainAdapter(data3,productPage.this);
                mAdapter3.notifyDataSetChanged();
                mRecycler3.setAdapter(mAdapter3);

            }else{
                try{

                }catch (Exception e){

                }

            }
        }
    }

}
