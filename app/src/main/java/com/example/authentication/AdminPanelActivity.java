package com.example.authentication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class AdminPanelActivity  extends AppCompatActivity {

    CardView cardQr ,cardDatabase ,cardLogout ,cardAbout;
    ImageView imageProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_panel);

        imageProfile = findViewById(R.id.Profile);
        cardAbout = findViewById(R.id.card_about_admin);
        cardLogout = findViewById(R.id.card_logout_admin);
        cardQr = findViewById(R.id.card_scan_admin);
        cardDatabase = findViewById(R.id.card_database_admin);




        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanelActivity.this, AdminProfileActivity.class);
                startActivity(intent);
            }
        });


        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(AdminPanelActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle("Logout");
                dialog.setMessage("Are you sure you want to exit?" );
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        //Action for loggout
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(AdminPanelActivity.this, AdminSignInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Action for "Cancel".
                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.show();




            }
        });

        cardQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminPanelActivity.this, AdminScanIntentActivity.class);
                startActivity(intent);
                finish();

            }
        });
        cardDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(AdminPanelActivity.this, AdminWebView.class);
                startActivity(intent);


            }
        });
        cardAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(AdminPanelActivity.this, AdminReportsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}