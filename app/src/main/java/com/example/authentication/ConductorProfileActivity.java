package com.example.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;



public class ConductorProfileActivity extends AppCompatActivity {

    TextView AccountName, AccountEmail;
    Button home,logout;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conductor_profile);

        AccountName  = findViewById(R.id.AccountName);
        AccountEmail  = findViewById(R.id.AccountEmail);


        AccountName.setText(GlobalVariable.currentUser.getName());
        AccountEmail.setText(GlobalVariable.currentUser.getEmail());

        home = findViewById(R.id.buttonHome);
        logout = findViewById(R.id.buttonLogout);

        auth=FirebaseAuth.getInstance();



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ConductorProfileActivity.this, ConductorHomeActivity.class);

                startActivity(intent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(ConductorProfileActivity.this, ConductorSignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}
