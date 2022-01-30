package com.example.authentication;



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

public class AdminSignInActivity extends AppCompatActivity {
    private EditText emailEt,passwordEt;
    private Button SignInButton;
    private TextView SignUpTv;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth2;
    ImageView change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_signin);

        emailEt=findViewById(R.id.email);
        passwordEt=findViewById(R.id.password);
        SignInButton=findViewById(R.id.login);
        progressDialog=new ProgressDialog(this);
        SignUpTv=findViewById(R.id.signUpTv);
        change = findViewById(R.id.change);
        firebaseAuth2=FirebaseAuth.getInstance();


        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminSignInActivity.this, MenuActivity.class);
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
                Intent intent=new Intent(AdminSignInActivity.this,AdminSignUpActivity.class);
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
        firebaseAuth2.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
//
                    FirebaseDatabase.getInstance().getReference("Administrators")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                public User currentUser;

                                @Override
                                public void onDataChange(@NonNull DataSnapshot DataSnapshot) {

                                    currentUser = DataSnapshot.getValue(User.class);

                                    GlobalVariable.currentUser = DataSnapshot.getValue(User.class);

                                    Intent admin = new Intent(AdminSignInActivity.this,AdminPanelActivity.class);

                                    startActivity(admin);
                                    Toast.makeText(AdminSignInActivity.this,"Login Successfully",Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }

                            });

                }
                else{
                    Toast.makeText(AdminSignInActivity.this,"Sign in fail!",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
    //
    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth2.getCurrentUser() !=null){

            Toast.makeText(AdminSignInActivity.this,"Already Login!",Toast.LENGTH_SHORT).show();
            FirebaseDatabase.getInstance().getReference("Administrators")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot DataSnapshot) {

                            GlobalVariable.currentUser = DataSnapshot.getValue(User.class);

                            Intent admin = new Intent(AdminSignInActivity.this, AdminProfileActivity.class);
                            startActivity(admin);
                            finish();
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

        }else{
            Toast.makeText(AdminSignInActivity.this,"User not Logged in :(",Toast.LENGTH_SHORT).show();
        }
    }
}
