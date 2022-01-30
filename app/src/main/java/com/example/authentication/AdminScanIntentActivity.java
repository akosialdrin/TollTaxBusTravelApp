package com.example.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AdminScanIntentActivity extends AppCompatActivity {
    Button QrBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_scan_intent);

    QrBtn = findViewById(R.id.Scannerbtn);


        Toolbar toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar2.setTitleTextAppearance(this, R.style.Acme);

        toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                Intent intent=new Intent(AdminScanIntentActivity.this, AdminPanelActivity.class);
                startActivity(intent);
                finish();

            }
        });





    QrBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent=new Intent(AdminScanIntentActivity.this, AdminQrCodeScannerActivity.class);
            startActivity(intent);


        }
    });



    }

}
