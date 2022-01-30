package com.example.authentication;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class ConductorHomeActivity extends AppCompatActivity {

  CardView cardCreate,cardLogout;
    ImageView imageProfile2;
     //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.conductor_home);


    imageProfile2 = findViewById(R.id.profile2);
    cardCreate = findViewById(R.id.cardCreate);
    cardLogout = findViewById(R.id.cardLogout);

    imageProfile2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ConductorHomeActivity.this, ConductorProfileActivity.class);
            startActivity(intent);
        }
    });




    cardCreate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent=new Intent(ConductorHomeActivity.this, ConductorCreateActivity.class);
            startActivity(intent);


        }
    });


    cardLogout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            AlertDialog.Builder dialog = new AlertDialog.Builder(ConductorHomeActivity.this);
            dialog.setCancelable(false);
            dialog.setTitle("Logout");
            dialog.setMessage("Are you sure you want to exit?" );
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //Action for loggout
                    FirebaseAuth.getInstance().signOut();
                    Intent intent=new Intent(ConductorHomeActivity.this, ConductorSignInActivity.class);
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
}
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    //

    //
}
