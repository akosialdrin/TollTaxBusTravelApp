package com.example.authentication;


import android.content.Intent;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AdminReportsActivity extends AppCompatActivity {
    DatabaseReference dbref;
    ImageView pPDF;
    private TextView Tnbus, Tnairbus;
    private int counttotalnumbus = 0;
    private int counttotalnumairbus = 0;
    DataObj dataObj = new DataObj();
    long docuNo = 0;
    SimpleDateFormat datePatternformat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");



    //new

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_reports);

        pPDF = findViewById(R.id.printpdf);

        Tnbus =findViewById(R.id.tnbus);
        Tnairbus =findViewById(R.id.tnairbus);


        callFindViewById();
        callOnclickListener();



        dbref = FirebaseDatabase.getInstance().getReference("QR Code Data");



        ///////////////////////////////////////////////////////////////////////////////////////
        // Initialising the reference to database

      /*  dbref = FirebaseDatabase.getInstance().getReference("QR Code Data");

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // getting a DataSnapshot for the location at the specified
                // relative path and getting in the link variable
                message = dataSnapshot.getValue(String.class);
            }

            // this will called when any problem
            // occurs in getting data
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // we are showing that error message in toast
                Toast.makeText(AdminReportsActivity.this, "Error Loading Pdf", Toast.LENGTH_SHORT).show();
            }
        });
        // After clicking here alert box will come
        pPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                CharSequence options[] = new CharSequence[]{
                        "Download",
                        "View",
                        "Cancel"
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Choose One");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // we will be downloading the pdf
                        if (which == 0) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message));
                            startActivity(intent);
                        }
                        // We will view the pdf
                        if (which == 1) {
                            Intent intent = new Intent(v.getContext(), AdminPrintPdf.class);
                            intent.putExtra("url", message);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
            }
        });
*/




        //////////////////////////////////////////////////////////////////////////////////////


dbref.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        docuNo = snapshot.getChildrenCount();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});


        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    counttotalnumbus = (int) dataSnapshot.getChildrenCount();
                    Tnbus.setText(Integer.toString(counttotalnumbus));




                }else{
                    Tnbus.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        Query query = FirebaseDatabase.getInstance().getReference("QR Code Data");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        //set featched value to recyclerview
                        counttotalnumairbus = (int) dataSnapshot.getChildrenCount();
                        Tnairbus.setText(Integer.toString(counttotalnumairbus));

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        Toolbar toolbar4 = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar4);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar4.setTitleTextAppearance(this, R.style.Acme);


        toolbar4.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                Intent intent=new Intent(AdminReportsActivity.this, AdminPanelActivity.class);
                startActivity(intent);
                finish();

            }
        });






    }
/////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////
    private void callOnclickListener() {

         pPDF.setOnClickListener(new View.OnClickListener() {
             @RequiresApi(api = Build.VERSION_CODES.KITKAT)
             @Override
             public void onClick(View v) {
            dataObj.date = new Date().getTime();
            dataObj.docuNo = docuNo +1;

            /*dbref.child(String.valueOf(docuNo+1)).setValue(dataObj);*/

            printPDF();
                 Toast.makeText(AdminReportsActivity.this,"Print Successfully as Pdf",Toast.LENGTH_SHORT).show();
             }
         });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void printPDF() {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint paint = new Paint();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(250 , 350 ,1).create();
        PdfDocument.Page myPage  = myPdfDocument.startPage(myPageInfo);
        Canvas canvas = myPage.getCanvas();

        paint.setTextSize(15.5f);
        paint.setColor(Color.rgb(0, 50, 250));

        canvas.drawText("Reports", 20,20, paint);
        paint.setTextSize(8.5f);

        canvas.drawText("Total of Bus:", 20,40, paint);

        canvas.drawText(String.valueOf(Tnbus.getText()),20, 80, paint);

        canvas.drawText("Total of AirCondition Bus:", 20,120, paint);

        canvas.drawText(String.valueOf(Tnairbus.getText()),20, 170, paint);



        canvas.drawText("Update @: "+datePatternformat.format(new Date().getTime()), 20,300, paint);

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(9f);
        canvas.drawText("All Rights Reserved @ 2022 Toll Tax Bus Travel",canvas.getWidth()/2, 330, paint);

        myPdfDocument.finishPage(myPage);
        File file = new File(this.getExternalFilesDir("/"), "TTBT.pdf");

        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        myPdfDocument.close();
    }

    private void callFindViewById() {

        pPDF = findViewById(R.id.printpdf);
        Tnbus =findViewById(R.id.tnbus);
        Tnairbus =findViewById(R.id.tnairbus);


    }

}