package com.example.abun.shoprite1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;

public class EditionPageForProduct extends AppCompatActivity {

    EditText ename;
    EditText catego;
    EditText stock;
    EditText description;
    EditText price;
    EditText discount;
    Button submit3;

    int catego1;
    String ename1;
    int stock1;
    String description1;
    float price1;
    float discount1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition_page_for_product);

        catego = (EditText) findViewById(R.id.editText10);
        ename = (EditText) findViewById(R.id.editText11);
        stock = (EditText) findViewById(R.id.editText12);
        description = (EditText) findViewById(R.id.editText13);
        price = (EditText) findViewById(R.id.editText14);
        discount = (EditText) findViewById(R.id.editText15);
        submit3 = (Button) findViewById(R.id.butiny);
        Intent intent = getIntent();
        final int pid = intent.getExtras().getInt("custidd");

        InputStream is= null;
        ConnectionClass conty = new ConnectionClass();
        conty.getProductDetails(pid,catego1,ename1,stock1,description1,price1,discount1,is);

        catego.setText(catego1);
        ename.setText(ename1);
        stock.setText(""+stock1);
        description.setText(description1);
        price.setText(""+price1);
        discount.setText(""+discount1);

        submit3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String Ename= ename.getText().toString();
                final String Edescription= description.getText().toString();
                final String EcreditT1= stock.getText().toString();
                final int Stock= Integer.parseInt(EcreditT1);
                final String Ecredit1= price.getText().toString();
                final float Eprice= Float.parseFloat(Ecredit1);
                final String Ecredit11= discount.getText().toString();
                final float Ediscount= Float.parseFloat(Ecredit11);
                ConnectionClass cont = new ConnectionClass();
                Boolean val=cont.updateProduct(pid,Ename,Stock,Edescription,Eprice,Ediscount);
                if(val= true){
                    Toast.makeText(EditionPageForProduct.this,"successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
