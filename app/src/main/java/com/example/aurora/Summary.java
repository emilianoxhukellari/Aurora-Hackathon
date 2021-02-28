package com.example.aurora;

import androidx.appcompat.app.AppCompatActivity;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Summary extends AppCompatActivity {
    private Button send;
    ImageView imagePicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        imagePicture = findViewById(R.id.summary_pic);

        Intent intent = getIntent();
        String issue3 = intent.getStringExtra(Camera.EXTRA_ISSUE2);
        String type3 = intent.getStringExtra(Camera.EXTRA_TYPE2);
        String comment3 = intent.getStringExtra(Camera.EXTRA_COMMENT2);
        String address = intent.getStringExtra(Camera.EXTRA_ADDRESS);
        double latitude = intent.getDoubleExtra(Camera.EXTRA_LAT, 0);
        double longitude = intent.getDoubleExtra(Camera.EXTRA_LON, 0);
        String name = new String("admin");
        Bundle extras = intent.getExtras();
        Bitmap picture = (Bitmap) extras.getParcelable("Bitmap");
        imagePicture.setImageBitmap(picture);

        TextView exactAddress = (TextView) findViewById(R.id.print_location);
        exactAddress.setText(address);

        TextView issueTitle = (TextView) findViewById(R.id.print_issue);
        issueTitle.setText(issue3);

        TextView category = (TextView) findViewById(R.id.print_category);
        category.setText(type3);

        TextView dateText = (TextView) findViewById(R.id.print_date);
        dateText.setText(currentDate);

        TextView timeText = (TextView) findViewById(R.id.print_time);
        timeText.setText(currentTime);

        TextView commentText = (TextView) findViewById(R.id.print_desc);
        commentText.setText(comment3);

        TextView nameText = (TextView) findViewById(R.id.print_name);
        nameText.setText(name);

        String message = new String("*** NEW REPORT *** \n" +  "Full Name: " + name + "\n" +
                "Issue Title: " + issue3 + "\n" + "Category: " + type3 + "\n" + "Date: " + currentDate + "\n"
                + "Time: " + currentTime + "\n" + "Location: " + address + "\n" + "Comment: " + comment3 + "\n");

        send = (Button) findViewById(R.id.send_btn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(type3.equals("Electrical") || type3.equals("Physical")) {
                    sendMail("enx190@aubg.edu", issue3, message);
                }
                else if(type3.equals("Hyper-Polluted") || type3.equals("Sewage")) {
                    sendMail("mng182@aubg.edu", issue3, message);
                }
                else if(type3.equals("Hazardous") || type3.equals("Acoustic")) {
                    sendMail("hnc190@aubg.edu", issue3, message);
                }
            }
        });
    }

    private void sendMail(String email, String subject, String message) {
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, email, subject, message);
        javaMailAPI.execute();
    }
}