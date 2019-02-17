package com.example.alex.audiogid.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;

import com.evernote.android.job.Job;
import com.example.alex.audiogid.MapsActivity;
import com.example.alex.audiogid.R;
import com.github.jorgecastillo.FillableLoader;
import com.github.jorgecastillo.FillableLoaderBuilder;
import com.github.jorgecastillo.clippingtransforms.PlainClippingTransform;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;

import java.nio.file.Paths;

public class SplashActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_COUNTER = "google";
    private SharedPreferences mSettings;
    FirebaseAuth mAuth;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
setContentView(R.layout.splash_screen_layout);
        getWindow().setBackgroundDrawableResource(R.color.transparent  );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = this;

        Thread xyz=new Thread()
        {
            public void run()
            {
                try {
                    sleep(3000);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    if(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
                        Intent abc = new Intent(SplashActivity.this, SetPermissionsActivity.class);
                        startActivity(abc);
                    }
                        else{
                    Intent abc = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(abc);
                }
                }
            }
        };
       xyz.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
