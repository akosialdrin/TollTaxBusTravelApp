package com.example.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminSignUpActivity extends AppCompatActivity {

    EditText ed_email, ed_pass, ed_con_pass,ed_name;

    private FirebaseAuth auth2;
    private FirebaseUser firebaseAdminUser;
    private DatabaseReference adminRef;
    ProgressDialog progressDialog;
    TextView SignInTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_signup);

        ed_name = findViewById(R.id.adminfullname2);
        ed_email = findViewById(R.id.adminemail);
        ed_pass = findViewById(R.id.adminpassword1);
        ed_con_pass = findViewById(R.id.adminpassword2);
        SignInTv=findViewById(R.id.adminsignInTv);
        progressDialog=new ProgressDialog(this);

        auth2 =FirebaseAuth.getInstance();

        adminRef = FirebaseDatabase.getInstance().getReference().child("Administrators");



        SignInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminSignUpActivity.this, AdminSignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void registerUser(View v){

        String name=ed_name.getText().toString();
        String email=ed_email.getText().toString();
        String password1=ed_pass.getText().toString();
        String password2=ed_con_pass.getText().toString();

        if(TextUtils.isEmpty(name)){
            ed_name.setError("Enter your full name");
            return;
        }
        else if(TextUtils.isEmpty(email)){
            ed_email.setError("Enter your email");
            return;
        }
        else if(TextUtils.isEmpty(password1)){
            ed_pass.setError("Enter your password");
            return;
        }
        else if(TextUtils.isEmpty(password2)){
            ed_con_pass.setError("Confirm your password");
            return;
        }
        else if(!password1.equals(password2)){
            ed_con_pass.setError("Different password");
            return;
        }
        else if(password1.length()<6){
            ed_pass.setError("Length should be > 6 characters");
            return;
        }
        else if(!isVallidEmail(email)){
            ed_email.setError("invalid email");
            return;
        }



        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        auth2.createUserWithEmailAndPassword(ed_email.getText().toString(),
                ed_pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
//
                    FirebaseDatabase.getInstance().getReference("Administrators")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .addListenerForSingleValueEvent(new ValueEventListener() {


                                @Override
                                public void onDataChange(@NonNull DataSnapshot DataSnapshot) {

                                    GlobalVariable.currentUser = DataSnapshot.getValue(User.class);

                                    Intent admin = new Intent(AdminSignUpActivity.this,AdminPanelActivity.class);

                                    startActivity(admin);
                                    Toast.makeText(AdminSignUpActivity.this,"Admin registered Successfully! ",Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }

                            });


                    createDatabaseValues();
                }
                else{
                    Toast.makeText(AdminSignUpActivity.this, "Admin could not be registered", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }


    private Boolean isVallidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public void createDatabaseValues()

    {
        firebaseAdminUser = auth2.getCurrentUser();
        String name = ed_name.getText().toString();
        String email = ed_email.getText().toString();
        String pass = ed_pass.getText().toString();
        String pass2 = ed_con_pass.getText().toString();

        Admins admins = new Admins(name,email,pass,pass2);

        adminRef.child(firebaseAdminUser.getUid()).setValue(admins).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AdminSignUpActivity.this, "Values stored in database", Toast.LENGTH_LONG).show();


                }
                else {
                    Toast.makeText(AdminSignUpActivity.this, "Values could not be stored", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
