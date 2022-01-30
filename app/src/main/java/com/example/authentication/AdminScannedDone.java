package com.example.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AdminScannedDone extends AppCompatActivity {
    Button QrBtn2 ,back2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_scanned_done);

        QrBtn2 = findViewById(R.id.Scannerbtn2);


        Toolbar toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar2.setTitleTextAppearance(this, R.style.Acme);

        toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                Intent intent=new Intent(AdminScannedDone.this, AdminPanelActivity.class);
                startActivity(intent);
                finish();

            }
        });





        QrBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(AdminScannedDone.this, AdminQrCodeScannerActivity.class);
                startActivity(intent);


            }
        });




    }

}
