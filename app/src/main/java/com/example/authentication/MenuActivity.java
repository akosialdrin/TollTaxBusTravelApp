package com.example.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
private ImageView Conductor;
private ImageView Admin;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Conductor = findViewById(R.id.conductor);
        Admin = findViewById(R.id.admin);
        progressDialog=new ProgressDialog(this);




        Conductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);

                Intent intent=new Intent(MenuActivity.this, ConductorSignInActivity.class);
                startActivity(intent);

            }
        });
        progressDialog.dismiss();

        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuActivity.this,AdminSignInActivity.class);
                startActivity(intent);

            }
        });


    }
}