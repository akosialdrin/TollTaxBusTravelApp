package com.example.authentication;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConductorCreateActivity extends AppCompatActivity {

    TextView todays_date_tv,currentTime;

    EditText editTextFullName;
    EditText editTextAddress;
    EditText editTextBusNo;
    Spinner spinnerDestination, spinnerBusType;

    ImageView qrcodeImage;
    Button buttonGenerator;
    ImageView back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conductor_create);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setTitleTextAppearance(this, R.style.Acme);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                Intent intent=new Intent(ConductorCreateActivity.this, ConductorHomeActivity.class);
                startActivity(intent);
                finish();

            }
        });



        //edit text for creating for fullname
        editTextFullName = (EditText) findViewById(R.id.editTextFullName);

        editTextAddress = (EditText) findViewById(R.id.editTextAddress);

        editTextBusNo = (EditText) findViewById(R.id.editTextBusNo);

        spinnerDestination = (Spinner) findViewById(R.id.spinnerDestination);

        spinnerBusType = (Spinner) findViewById(R.id.spinnerBus_type);

        todays_date_tv = (TextView) findViewById(R.id.todays_date_tv);

        todays_date_tv.setText(getTodaysDate());

        currentTime = (TextView) findViewById(R.id.currentTime);

        currentTime.setText(getCurrentDate());

        //button for creating QR
        buttonGenerator = (Button) findViewById(R.id.buttonGenerator);

        qrcodeImage = (ImageView) findViewById(R.id.imageViewQR);






        buttonGenerator.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

             //TextUtils
                if (TextUtils.isEmpty(editTextFullName.getText().toString())) {
                    editTextFullName.setError("Name must not be empty!");
                } else {
                    Toast.makeText(ConductorCreateActivity.this, editTextFullName.getText().toString(), Toast.LENGTH_SHORT);

                    if (TextUtils.isEmpty(editTextAddress.getText().toString())) {
                        editTextAddress.setError("Address must not be empty!");
                    } else {
                        Toast.makeText(ConductorCreateActivity.this, editTextAddress.getText().toString(), Toast.LENGTH_SHORT);

                        if (TextUtils.isEmpty(editTextBusNo.getText().toString())) {
                            editTextBusNo.setError("BusNo. must not be empty!");
                        } else {
                            Toast.makeText(ConductorCreateActivity.this, editTextBusNo.getText().toString(), Toast.LENGTH_SHORT);

                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                            try {
                                BitMatrix bitMatrix = multiFormatWriter.encode(
                                        "Full Name: " + editTextFullName.getText().toString() + " " +
                                                "Address: " + editTextAddress.getText().toString() + " " +
                                                "Bus No.: " + editTextBusNo.getText().toString() + " " +
                                                "Bus Type: " + spinnerBusType.getSelectedItem().toString() + " " +
                                                "Destination: " + spinnerDestination.getSelectedItem().toString() + " " +
                                                "Date: " + todays_date_tv.getText().toString() + " " +
                                                "Time: " + currentTime.getText().toString() + " "

                                        , BarcodeFormat.QR_CODE, 3000, 3000);

                                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                qrcodeImage.setImageBitmap(bitmap);

                                //saved to gallery
                                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "code_scanner"
                                        , null);

                                //


                                //clear's text in edit text
                                editTextFullName.setText("");
                                editTextAddress.setText("");
                                editTextBusNo.setText("");
                                Toast.makeText(ConductorCreateActivity.this, "Data generated successfully! and save to gallery", Toast.LENGTH_SHORT).show();

                                //passing picture to next activity


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }
        });
    }



    private  String getTodaysDate(){
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    }
    private  String getCurrentDate(){
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }
}
