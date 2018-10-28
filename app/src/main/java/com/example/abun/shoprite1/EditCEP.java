package com.example.abun.shoprite1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditCEP extends AppCompatActivity {

    EditText ename;
    EditText egender;
    EditText edob;
    EditText edesignation;
    EditText eaddress;
    EditText eemail;
    EditText ephone;
    EditText password;
    Button submit3;

    int dob;
    String ename1;
    String gender;
    int credit;
    String credity;
    String addr;
    String email;
    String passwol;
    int phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cep);

        ename = (EditText) findViewById(R.id.editText10);
        egender = (EditText) findViewById(R.id.editText11);
        edob = (EditText) findViewById(R.id.editText12);
        edesignation = (EditText) findViewById(R.id.editText13);
        eaddress = (EditText) findViewById(R.id.editText14);
        eemail = (EditText) findViewById(R.id.editText15);
        ephone = (EditText) findViewById(R.id.editText16);
        password = (EditText) findViewById(R.id.editText7);
        submit3 = (Button) findViewById(R.id.button312);

        Intent intt = getIntent();
        final int  name = intt.getExtras().getInt("name");

        ConnectionClass conty = new ConnectionClass();
        conty.getCustomerDetails(name,ename1,gender,addr,phone,dob,passwol,credit,credity);

        ename.setText(ename1);
        egender.setText(ename1);
        edob.setText(""+dob);
        edesignation.setText(credit);
        eaddress.setText(""+addr);
        eemail.setText(""+email);
        password.setText(passwol);
        ephone.setText(""+phone);

        submit3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String Ename= ename.getText().toString();
                final String Egender= egender.getText().toString();
                final String EcreditT1= edesignation.getText().toString();
                final int EcreditT= Integer.parseInt(EcreditT1);
                final String EPassword= password.getText().toString();
                final String Ecredit1= eemail.getText().toString();
                final int Ecredit= Integer.parseInt(Ecredit1);
                final String EAddress= eaddress.getText().toString();
                final String EDOb= edob.getText().toString();
                final String Ephone= ephone.getText().toString();
                Intent intent = getIntent();
                final int cid = intent.getExtras().getInt("custidd");
                ConnectionClass cont = new ConnectionClass();
                Boolean val=cont.updateCustomer(cid,Ename,Egender,EAddress,Ephone,EDOb,EPassword,Ecredit,EcreditT);
                if(val= true){
                    Toast.makeText(EditCEP.this,"successfully",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
