package com.example.alex.audiogid.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.alex.audiogid.MapsActivity;
import com.example.alex.audiogid.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 234;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_COUNTER = "google";
    private SharedPreferences mSettings;
    //Tag for the logs optional
    private static final String TAG = "LoginActivity";

    //creating a GoogleSignInClient object
    GoogleSignInClient mGoogleSignInClient;

    //And also a Firebase Auth object
    FirebaseAuth mAuth;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    Button mLoginButton;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton = (Button) findViewById(R.id.signin);
      //  progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();
        if (mSettings.contains(APP_PREFERENCES_COUNTER)) {

            AuthCredential credential = GoogleAuthProvider.getCredential(mSettings.getString(APP_PREFERENCES_COUNTER, "No name defined"), null);

            Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity(intent);
            //Now using firebase we are signing in the user here
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                finish();
                            }

                            // ...
                        }
                    });

        }


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        mLoginButton. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                //authenticating with firebase
                firebaseAuthWithGoogle(account);
                progressDialog.show();
            } catch (ApiException e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_COUNTER, acct.getIdToken());
        editor.apply();
        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Now using firebase we are signing in the user here
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                              Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                         // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                        startActivity(intent);
                           finish();
                          progressDialog.dismiss();

                        }

                        // ...
                    }
                });
    }


    //this method is called on click
    private void signIn() {
        //getting the google signin intent
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

}

