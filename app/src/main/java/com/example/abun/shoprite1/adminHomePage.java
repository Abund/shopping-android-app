package com.example.abun.shoprite1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class adminHomePage extends AppCompatActivity {

    private Button customerAdd;
    private Button customerEdit;
    private Button employeeAdd;
    private Button employeeEdit;
    private Button productAdd;
    private Button productEdit;
    private Button productReports;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        customerAdd = (Button) findViewById(R.id.addcust);
        customerEdit = (Button) findViewById(R.id.editcust);
        employeeAdd = (Button) findViewById(R.id.addEmp);
        employeeEdit = (Button) findViewById(R.id.editEmp);
        productAdd = (Button) findViewById(R.id.addPro);
        productEdit = (Button) findViewById(R.id.editPro);
        productReports = (Button) findViewById(R.id.proRe);
        button = (Button) findViewById(R.id.logout1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminHomePage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        customerEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(adminHomePage.this, adminCustomerSearch.class);
                startActivity(at);
            }
        });
        customerAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(adminHomePage.this, addCustomerPage.class);
                startActivity(at);
            }
        });
        employeeEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(adminHomePage.this, adminEmployeeSearch.class);
                startActivity(at);
            }
        });
        employeeAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(adminHomePage.this, addEmployeePage.class);
                startActivity(at);
            }
        });
        productEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(adminHomePage.this, adminProductSearch.class);
                startActivity(at);
            }
        });
        productAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent at = new Intent(adminHomePage.this, addProductPageAdmin.class);
                startActivity(at);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_admin_menu,menu);
        return true;
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_admin_menu,menu);
        //return super.onCreateOptionsMenu(menu);
    }
}
