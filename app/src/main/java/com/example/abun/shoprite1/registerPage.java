package com.example.abun.shoprite1;

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

public class registerPage extends Activity  {

    Button signup;
    EditText name, gender, address, phone,Dob,password,email,cardnumber,cardType;
    ConnectionClass db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpage);
        signup = (Button) findViewById(R.id.signup);
        name = (EditText) findViewById(R.id.name1);
        gender = (EditText) findViewById(R.id.gender1);
        address = (EditText) findViewById(R.id.address1);
        phone = (EditText) findViewById(R.id.phone1);
        Dob = (EditText) findViewById(R.id.dob);
        password = (EditText) findViewById(R.id.password2);
        email = (EditText) findViewById(R.id.emailadd);
        cardnumber = (EditText) findViewById(R.id.cardnumber);
        cardType = (EditText) findViewById(R.id.cardtype);

        db = new ConnectionClass();


        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String n = name.getText().toString();
                String g = gender.getText().toString();
                String a = address.getText().toString();
                int p = Integer.parseInt(phone.getText().toString());
                String pp = phone.getText().toString();
                String p1 = password.getText().toString();
                String d = Dob.getText().toString();
                String e = email.getText().toString();
                int c1 = Integer.parseInt(cardnumber.getText().toString());
                String c2 = cardType.getText().toString();

                if(n.equals("") || g.equals("")||a.equals("") || p==0||p1.equals("") || d.equals("")||c1==0 || c2.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"field or fields are empty",Toast.LENGTH_SHORT).show();
                }else{
                    boolean val = db.insertDataCustomer(n,g,a,pp,d,p1,e,c1,c2);
                    if(val == true){
                        Toast.makeText(getApplicationContext(),"Registration successful",Toast.LENGTH_SHORT).show();
                        Intent at = new Intent(registerPage.this, productPage.class);
                        startActivity(at);
                    }else{
                        Toast.makeText(getApplicationContext(),"Problem occured please try later",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}
