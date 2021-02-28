package com.example.aurora;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.os.Build;
import android.os.Bundle;
import android.location.Geocoder;
import android.location.Location;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Camera extends AppCompatActivity {
    private ImageView picture;
    private Button camera_btn;
    private Button summary_btn;
    public Bitmap captureImage;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private Geocoder geocoder;
    public double latitude;
    public double longitude;

    public String exactAddress;
    public String issue2;
    public String type2;
    public String desc2;

    public static final String EXTRA_ADDRESS = "com.example.aurora.EXTRA_ADDRESS";
    public static final String EXTRA_LAT = "com.example.aurora.EXTRA_LAT";
    public static final String EXTRA_LON = "com.example.aurora.EXTRA_LON";
    public static final String EXTRA_ISSUE2 = "com.example.aurora.EXTRA_ISSUE2";
    public static final String EXTRA_TYPE2 = "com.example.aurora.EXTRA_TYPE2";
    public static final String EXTRA_COMMENT2 = "com.example.aurora.EXTRA_COMMENT2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Intent intent = getIntent();
        issue2 = intent.getStringExtra(FormFill.EXTRA_ISSUE);
        type2 = intent.getStringExtra(FormFill.EXTRA_TYPE);
        desc2 = intent.getStringExtra(FormFill.EXTRA_COMMENT);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        picture = findViewById(R.id.picture);
        camera_btn = findViewById(R.id.camera_btn);
        summary_btn = findViewById(R.id.summary_btn);

        if (ContextCompat.checkSelfPermission(Camera.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Camera.this, new String[] {
                            Manifest.permission.CAMERA
                    },
                    100);
        }

        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        fusedLocationProviderClient.getLastLocation()
                                .addOnSuccessListener(new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        if (location != null) {
                                            try {
                                                geocoder = new Geocoder(Camera.this, Locale.getDefault());
                                                latitude = location.getLatitude();
                                                longitude = location.getLongitude();

                                                List<Address> addresses = geocoder.getFromLocation(
                                                        latitude,
                                                        longitude,
                                                        1
                                                );

                                                exactAddress = addresses.get(0).getAddressLine(0);

                                            }
                                            catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                });
                    }
                    else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        }
                    }
                }
            }
        });
        summary_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSummary();
            }
        });
    }
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100) {
            captureImage =(Bitmap) data.getExtras().get("data");
            picture.setImageBitmap(captureImage);
        }
    }
    public void openSummary() {
        Intent intent = new Intent(this, Summary.class);
        intent.putExtra(EXTRA_ADDRESS, exactAddress);
        intent.putExtra(EXTRA_LAT, latitude);
        intent.putExtra(EXTRA_LON, longitude);
        intent.putExtra(EXTRA_ISSUE2, issue2);
        intent.putExtra(EXTRA_TYPE2, type2);
        intent.putExtra(EXTRA_COMMENT2, desc2);
        Bundle extras = new Bundle();
        extras.putParcelable("Bitmap", captureImage);
        intent.putExtras(extras);
        startActivity(intent);
    }
}