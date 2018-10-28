package com.example.abun.shoprite1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addEmployeePage extends AppCompatActivity {

    private EditText name;
    private EditText gender;
    private EditText designation;
    private EditText password;
    private EditText address;
    private EditText dob;
    private EditText phone;
    private Button submit;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee_page);

        name = (EditText) findViewById(R.id.addEmployeeName);
        gender = (EditText) findViewById(R.id.addEmGender);
        designation = (EditText) findViewById(R.id.addDesignation);
        password = (EditText) findViewById(R.id.addPassword);
        address = (EditText) findViewById(R.id.addAddress1);
        dob = (EditText) findViewById(R.id.addDob);
        phone = (EditText) findViewById(R.id.addPhone1);
        submit = (Button) findViewById(R.id.addPSubmit);
        email = (EditText) findViewById(R.id.addEmail);



        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String Ename= name.getText().toString();
                final String Egender= gender.getText().toString();
                final String Edesignation= designation.getText().toString();
                final String EPassword= password.getText().toString();
                final String Eemail= email.getText().toString();
                final String EAddress= address.getText().toString();
                final String EDOb= dob.getText().toString();
                final String Ephone= phone.getText().toString();
                final int idy =0;
                ConnectionClass cont = new ConnectionClass();
                Boolean val=cont.updateEmployee(idy,Ename,Egender,EDOb,Edesignation,EAddress,Eemail,Ephone,EPassword);
                if(val= true){
                    Toast.makeText(addEmployeePage.this,"successfully",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
