package com.example.abun.shoprite1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
//import com.example.abun.shoprite1.databaseHelper;
import java.sql.Connection;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.String;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import android.os.AsyncTask;
import android.annotation.SuppressLint;
import android.util.Log;

public class MainActivity extends Activity {
    ConnectionClass connectionClass;
    Button employee, admin, submit, reg;
    EditText user, password;
    ConnectionClass db;
    ProgressBar progressBar;
    TextView forget;
    UserSessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new ConnectionClass();
        session = new UserSessionManager(getApplicationContext());
        employee = (Button) findViewById(R.id.employee);
        admin = (Button) findViewById(R.id.admin);
        submit = (Button) findViewById(R.id.button1);
        reg = (Button) findViewById(R.id.register);
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        forget = (TextView) findViewById(R.id.forgetPass);

        employee.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(MainActivity.this, employeeLogin.class);
                startActivity(at);
            }
        });

        forget.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(MainActivity.this, forgetPassowordPage.class);
                startActivity(at);
            }
        });

        admin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(MainActivity.this, adminLogin.class);
                startActivity(at);
            }
        });

        reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(MainActivity.this, registerPage.class);
                startActivity(at);
            }
        });
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //String e = user.getText().toString();
                //String p = password.getText().toString();
                getist g = new getist();
                g.execute("");
            }
        });

    }
    class getist extends AsyncTask<String, String ,String>{

        String z="";
        Boolean isSuccess = false;
        String e = user.getText().toString();
        String p = password.getText().toString();
        @Override
        protected void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String...params){
            if(e.equals("") || p.equals("")){
                z="please enter user isd and password";
            }else{
                try {

                    Connection con = db.getConnection();
                    ResultSet resultSet = null;
                    if (con == null) {
                        z = "error in connection with sql server";
                    } else {
                        String query = "select * from Customer_Details where Customer_Name =? and Password=?";
                        PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        stmt.setString(1, e);
                        stmt.setString(2, p);

                        resultSet = stmt.executeQuery();
                        if (resultSet.next()) {
                            //Intent at = new Intent(MainActivity.this, productPage.class);
                            //startActivity(at);
                            session.createUserLoginSession(e,
                                    p);

                            // Starting MainActivity
                            Intent i = new Intent(getApplicationContext(), productPage.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


                            // Add new Flag to start new Activity
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);

                            finish();
                            isSuccess = true;
                            z="login successfullY";
                        } else {
                            z = "invalid credentials";
                            isSuccess=false;
                        }
                    }
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    Log.e( "error here: ",e.getMessage());
                    //return  false;
                    isSuccess=false;
                    z="exceptions;";
                }
            }
            //String z="";
            return z;
        }

        @Override
        protected void onPostExecute(String r){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this,r,Toast.LENGTH_SHORT).show();

            if(isSuccess){
                Toast.makeText(MainActivity.this,r,Toast.LENGTH_SHORT).show();
            }
        }

    }

}
