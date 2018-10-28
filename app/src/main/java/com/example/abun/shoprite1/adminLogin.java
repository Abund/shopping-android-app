package com.example.abun.shoprite1;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by Abun on 5/22/2018.
 */

public class adminLogin extends Activity{

    EditText email,password;
    Button submit;
    ConnectionClass db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminloginpage);

        submit = (Button) findViewById(R.id.aSubmit);
        email = (EditText) findViewById(R.id.aEmail);
        password = (EditText) findViewById(R.id.aPassword);
        db = new ConnectionClass();

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String pass = password.getText().toString();
                String em = email.getText().toString();
                if(pass.equals("") || em.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"field or fields are empty",Toast.LENGTH_SHORT).show();
                }else{
                    boolean val = false;
                    try {
                        val = db.verifyAdmin(em,pass);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if(val == true){
                        Toast.makeText(getApplicationContext(),"login successful",Toast.LENGTH_SHORT).show();
                        Intent at = new Intent(adminLogin.this, adminHomePage.class);
                        startActivity(at);
                    }else{
                        Toast.makeText(getApplicationContext(),"Problem occured please try later",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}


