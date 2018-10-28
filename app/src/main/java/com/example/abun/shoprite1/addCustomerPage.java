package com.example.abun.shoprite1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.sourceforge.jtds.jdbc.DateTime;

public class addCustomerPage extends AppCompatActivity {

    private EditText name;
    private EditText gender;
    private EditText phone;
    private EditText password;
    private EditText creditcard;
    private EditText address;
    private EditText dob;
    private EditText creditcardtype;
    private Button submit;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_page);
        name = (EditText) findViewById(R.id.addCustomerName);
        gender = (EditText) findViewById(R.id.addGender);
        phone = (EditText) findViewById(R.id.addPhone);
        password = (EditText) findViewById(R.id.addPassword);
        creditcard = (EditText) findViewById(R.id.addCreditCard);
        address = (EditText) findViewById(R.id.addAddress);
        dob = (EditText) findViewById(R.id.addDofB);
        creditcardtype = (EditText) findViewById(R.id.addCreditCardType);
        submit = (Button) findViewById(R.id.addCustomerSubmit);
        email = (EditText) findViewById(R.id.emailing);



        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String Cname= name.getText().toString();
                final String Cgender= gender.getText().toString();
                final String CPhone= phone.getText().toString();
                final String CPassword= password.getText().toString();
                final String Cemail= email.getText().toString();
                final String Ccreditcard= creditcard.getText().toString();
                 int credit = Integer.parseInt(creditcard.getText().toString());
                final String CAddress= address.getText().toString();
                final String CDOb= dob.getText().toString();
                final String Ccreditcardtype= creditcardtype.getText().toString();
                ConnectionClass cont = new ConnectionClass();
                Boolean val=cont.insertDataCustomer(Cname,Cgender,CAddress,CPhone,CDOb,CPassword,Cemail,credit,Ccreditcardtype);
                if(val= true){
                    Toast.makeText(addCustomerPage.this,"successfully",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
