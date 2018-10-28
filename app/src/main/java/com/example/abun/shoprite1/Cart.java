package com.example.abun.shoprite1;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class Cart extends Fragment {
    View view;
    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    ConnectionClass db;
    UserSessionManager session;
    String name1;
    private ArrayList<OrderTable> data;
    private ArrayList<ProductPageRetrive> data1;
    int productid;
    private Button button;
    int total=0;
    private TextView totaling;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container,Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.activity_cart,container,false);

        db = new ConnectionClass();
        //session = new UserSessionManager(getActivity());
        data = new ArrayList<OrderTable>();
        data1 = new ArrayList<ProductPageRetrive>();
        SyncData orderData = new SyncData();
        orderData.execute("");
        session = new UserSessionManager(getActivity().getApplicationContext());
        mRecycler = (RecyclerView) view.findViewById(R.id.cartRecycler_view);
        button = (Button) view.findViewById(R.id.button75);
        totaling = (TextView) view.findViewById(R.id.showingTotal);
        mRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecycler.setLayoutManager(mLayoutManager);


        if(session.checkLogin())
            getActivity().finish();

        // get user data from session
        HashMap<String,String> user = session.getUserDetails();

        // get name
        this.name1 = user.get(UserSessionManager.KEY_NAME);

        // get email
        String email = user.get(UserSessionManager.KEY_EMAIL);
        return view;


    }

    private class SyncData extends AsyncTask<String ,String,String> {
        String msg="Internet/DB_Credentials/Windows_FireWall_TurnOn Error, see android monitor in the buttom for details";
        ProgressDialog progress;
        boolean success = false;

        @Override
        protected void onPreExecute(){
            progress = ProgressDialog.show(getActivity(),"Synchronising","RecyclerView loading! please wait...",true);
        }

        @Override
        protected String doInBackground(String...strings){
            try {
                Connection con = db.getConnection();
                Connection con1 = db.getConnection();
                ConnectionClass cont= new ConnectionClass();

                ResultSet resultSet = null;
                ResultSet resultSet1 = null;

                if (con == null) {
                    msg="No Data found";
                    success = false;
                }else{
                    int customerid= cont.selectCustomerId(name1);
                    String query = "SELECT * FROM Order_Details where Status ='no purchase' and Customer_ID =" + customerid ;
                    PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    resultSet = stmt.executeQuery();

                    while (resultSet.next()) {
                        try {
                            productid = resultSet.getInt("Product_ID");
                            //data.add(new ProductPageRetrive(resultSet.getBinaryStream("Image"),resultSet.getInt("Product_ID"), resultSet.getString("Product_Name"), resultSet.getInt("Price"),resultSet.getInt("Stock"), resultSet.getString("Description"), resultSet.getInt("Discount")));
                            data.add(new OrderTable(resultSet.getInt("Order_ID"), resultSet.getInt("Customer_ID"), resultSet.getInt("Employee_ID"),resultSet.getInt("Product_ID"), resultSet.getInt("Quantity"), resultSet.getDouble("Amount")));

                            String query1 = "SELECT * FROM Product_Details where Product_ID ="+productid;
                            PreparedStatement stmt1 = con1.prepareStatement(query1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            resultSet1 = stmt1.executeQuery();
                            while (resultSet1.next()) {
                                try {
                                    //data1.add(new OrderTable(resultSet1.getInt("Order_ID"), resultSet1.getInt("Customer_ID"), resultSet1.getInt("Employee_ID"),resultSet1.getInt("Product_ID"), resultSet1.getInt("Quantity"), resultSet1.getDouble("Amount")));
                                    data1.add(new ProductPageRetrive(resultSet1.getBinaryStream("Image"),resultSet1.getInt("Product_ID"), resultSet1.getString("Product_Name"), resultSet1.getInt("Price"),resultSet1.getInt("Stock"), resultSet1.getString("Description"), resultSet1.getInt("Discount")));
                                    total+=resultSet1.getInt("Price")*resultSet.getInt("Quantity");
                                }catch(Exception ex){
                                    ex.printStackTrace();
                                    Log.e( "error here2: ",ex.getMessage());
                                    Log.e( "error here2: ",ex.getLocalizedMessage());
                                }
                            }
                        }catch(Exception ex){
                            ex.printStackTrace();
                            Log.e( "error here1: ",ex.getMessage());
                        }
                    }
                    msg ="found";
                    success=true;
                }
            }catch(Exception e){
                e.printStackTrace();
                Log.e( "error here11: ",e.getMessage());
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
            Toast.makeText(getActivity(),msg+"",Toast.LENGTH_LONG).show();
            if(success == true){

                mAdapter = new cartAdapter(data,data1,getActivity().getBaseContext());
                mAdapter.notifyDataSetChanged();
                mRecycler.setAdapter(mAdapter);
                totaling.setText(total+"");
            }else{
                try{

                }catch (Exception e){
                    Log.e( "error here: ",e.getMessage());
                }

            }
        }
    }
}
