package com.example.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ForgetPassConductorActivity extends AppCompatActivity {

     TextView SignUpTv;
    Button ForgetPass;
    EditText ForgetEmail;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_pass_conductor);

        SignUpTv=findViewById(R.id.signUpTv);
        ForgetPass = findViewById(R.id.forgetPassword);
        ForgetEmail = findViewById(R.id.forgetEmail);




        //

        SignUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ForgetPassConductorActivity.this, ConductorSignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });


        ForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = ForgetEmail.getText().toString();
                if (email.isEmpty()){
                    Toast.makeText(ForgetPassConductorActivity.this, "Please provide your email", Toast.LENGTH_SHORT).show();
                }else{
                    ForgetPass();
                    
                }
            }
        });






    }

    private void ForgetPass() {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgetPassConductorActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgetPassConductorActivity.this,ConductorSignInActivity.class));
                            finish();
                        }else{
                            Toast.makeText(ForgetPassConductorActivity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
