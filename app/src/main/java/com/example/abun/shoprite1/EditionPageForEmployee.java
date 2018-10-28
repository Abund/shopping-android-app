package com.example.abun.shoprite1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;

public class EditionPageForEmployee extends AppCompatActivity {

    EditText ename;
    EditText egender;
    EditText edob;
    EditText edesignation;
    EditText eaddress;
    EditText eemail;
    EditText password;
    EditText ephone;
    Button submit3;

    int dob;
    String ename1;
    String gender;
    String desig;
    String addr;
    String email;
    String passwol;
    int phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition_page_for_employee);

        ename = (EditText) findViewById(R.id.editText10);
        egender = (EditText) findViewById(R.id.editText11);
        edob = (EditText) findViewById(R.id.editText12);
        edesignation = (EditText) findViewById(R.id.editText13);
        eaddress = (EditText) findViewById(R.id.editText14);
        eemail = (EditText) findViewById(R.id.editText15);
        password = (EditText) findViewById(R.id.editText70);
        ephone = (EditText) findViewById(R.id.editText16);
        submit3 = (Button) findViewById(R.id.butiny);

        Intent intt = getIntent();
        final int  Empid = intt.getExtras().getInt("Empid");

        ConnectionClass conty = new ConnectionClass();
        conty.getEmployeeDetails(Empid,ename1,gender,dob,desig,addr,email,passwol,phone);

        ename.setText(ename1);
        egender.setText(ename1);
        edob.setText(""+dob);
        edesignation.setText(desig);
        eaddress.setText(""+addr);
        eemail.setText(""+email);
        password.setText(passwol);
        ephone.setText(""+phone);

        submit3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = getIntent();
                final String Ename= ename.getText().toString();
                final String Egender= egender.getText().toString();
                final String Edesignation= edesignation.getText().toString();
                final String EPassword= password.getText().toString();
                final String Eemail= eemail.getText().toString();
                final String EAddress= eaddress.getText().toString();
                final String EDOb= edob.getText().toString();
                final String Ephone= ephone.getText().toString();
                final int Eid = intent.getExtras().getInt("Empid");
                ConnectionClass cont = new ConnectionClass();
                Boolean val=cont.updateEmployee(Eid,Ename,Egender,EDOb,Edesignation,EAddress,Eemail,Ephone,EPassword);
                if(val= true){
                    Toast.makeText(EditionPageForEmployee.this,"successfully",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
