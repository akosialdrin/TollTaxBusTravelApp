package com.example.authentication;



import static com.example.authentication.GlobalVariable.currentUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConductorSignInActivity extends AppCompatActivity {
    EditText emailEt,passwordEt;
     Button SignInButton;
     TextView SignUpTv ,ForgetPassword;
     ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    ImageView change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.conductor_signin);



        emailEt=findViewById(R.id.email);
        passwordEt=findViewById(R.id.password);

        SignInButton=findViewById(R.id.login);
        progressDialog=new ProgressDialog(this);
        SignUpTv=findViewById(R.id.signUpTv);
        ForgetPassword=findViewById(R.id.ForgetPassword);
        change = findViewById(R.id.change);
        firebaseAuth=FirebaseAuth.getInstance();


        ForgetPassword.setOnClickListener(new View.OnClickListener() {
                   @Override
                  public void onClick(View v) {
                        Intent intent = new Intent(ConductorSignInActivity.this, ForgetPassConductorActivity.class);
                        startActivity(intent);
                        finish();
                           }
                         });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ConductorSignInActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });




        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        SignUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConductorSignInActivity.this, ConductorSignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



    private void Login(){
        String email=emailEt.getText().toString();
        String password=passwordEt.getText().toString();
        if(TextUtils.isEmpty(email)){
            emailEt.setError("Enter your email");
            return;
        }
        else if(TextUtils.isEmpty(password)){
            passwordEt.setError("Enter your password");
            return;
        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ConductorSignInActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot DataSnapshot) {
                                    currentUser = DataSnapshot.getValue(User.class);

                                    GlobalVariable.currentUser = DataSnapshot.getValue(User.class);

                                    Intent myaccountconductor = new Intent(ConductorSignInActivity.this, ConductorHomeActivity.class);

                                    startActivity(myaccountconductor);
                                    Toast.makeText(ConductorSignInActivity.this,"Successfully sign in! ",Toast.LENGTH_SHORT).show();
                                    finish();
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                }
                else{
                    Toast.makeText(ConductorSignInActivity.this,"Sign In fail!",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() !=null){

            Toast.makeText(ConductorSignInActivity.this,"Already Login!",Toast.LENGTH_SHORT).show();
            FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot DataSnapshot) {
                            currentUser = DataSnapshot.getValue(User.class);

                            Intent intent = new Intent(ConductorSignInActivity.this, ConductorProfileActivity.class);
                            startActivity(intent);
                            finish();
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

        }else{
            Toast.makeText(ConductorSignInActivity.this,"User not Logged in :(",Toast.LENGTH_SHORT).show();
        }
    }
}
