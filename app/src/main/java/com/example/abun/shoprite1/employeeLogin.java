package com.example.abun.shoprite1;

import android.os.Bundle;

/**
 * Created by Abun on 5/22/2018.
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
//import com.example.abun.shoprite1.databaseHelper;
import android.widget.Toast;
import java.lang.String;
import java.sql.SQLException;

public class employeeLogin  extends Activity {
    EditText email,password;
    Button submit;
    ConnectionClass db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employeelogin);
        submit = (Button) findViewById(R.id.eSubmit);
        email = (EditText) findViewById(R.id.eEmail);
        password = (EditText) findViewById(R.id.ePassword);
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
                        val = db.verifyEmployee(em,pass);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if(val == true){
                        Toast.makeText(getApplicationContext(),"login successful",Toast.LENGTH_SHORT).show();
                        Intent at = new Intent(employeeLogin.this, EmployeePage.class);
                        startActivity(at);
                    }else{
                        Toast.makeText(getApplicationContext(),"Problem occured please try later",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}
