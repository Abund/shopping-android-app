package com.example.abun.shoprite1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addProductPageAdmin extends AppCompatActivity {

    private EditText name;
    private EditText category;
    private EditText stock;
    private EditText description;
    private EditText price;
    private EditText discount;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_page_admin);

        name = (EditText) findViewById(R.id.addProductName1);
        category = (EditText) findViewById(R.id.addCategoryId);
        stock = (EditText) findViewById(R.id.addStock);
        description = (EditText) findViewById(R.id.addDescription);
        price = (EditText) findViewById(R.id.addProductPrice);
        discount = (EditText) findViewById(R.id.addDiscount);
        submit = (Button) findViewById(R.id.addProductSubmit11);



        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String Ename= name.getText().toString();
                final String Ecategory= category.getText().toString();
                final int category = Integer.parseInt(Ecategory);
                final String Estock= stock.getText().toString();
                final int stock = Integer.parseInt(Estock);
                final String Eprice= price.getText().toString();
                final float price= Float.parseFloat(Eprice);
                final String Ediscription= description.getText().toString();
                final String Ediscount= discount.getText().toString();
                final float discount= Float.parseFloat(Ediscount);
                ConnectionClass cont = new ConnectionClass();
                Boolean val=cont.insertDataProduct(category,Ename,stock,Ediscription,price,discount);
                if(val= true){
                    Toast.makeText(addProductPageAdmin.this,"successfully",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
