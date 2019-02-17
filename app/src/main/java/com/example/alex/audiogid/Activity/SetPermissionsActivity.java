package com.example.alex.audiogid.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;


import com.example.alex.audiogid.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;

public class SetPermissionsActivity  extends AppCompatActivity {
private CardView cardView;
private Activity activity;
    private int a = 0;
    private Context context;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_permissions);
        getWindow().setBackgroundDrawableResource(R.color.transparent  );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
context = this;
activity = this;
cardView = findViewById(R.id.buttonsetPermissions);
cardView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent abc=new Intent(SetPermissionsActivity.this,SplashActivity.class);
                        startActivity(abc);
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {

                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                      }
                }).check();


    }
});

    }
}