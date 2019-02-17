package com.example.alex.audiogid.Activity.BestPlace.SecondVarians;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.alex.audiogid.R;


public class BestPlacesV2 extends AppCompatActivity {


    private Fragment fragment;
    private static String FRAGMENT_TAG = "";

    public static final String NAV_ITEM_ID = "navItemId";
    private int mNavItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_places_v2);

                switchFragment();

    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==Activity.RESULT_OK){

            Intent intent = new Intent();
            intent.putExtra("First",data.getStringExtra("Firstone"));
            intent.putExtra("Second",data.getStringExtra("Secondone"));
            setResult(RESULT_OK,intent);
            finish();

        }

    }

    /**
     * Handles Navigation Logic
     */
    private void switchFragment() {
                fragment = new FragmentUS();
                FRAGMENT_TAG = "US";
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, FRAGMENT_TAG).commit();

    }




}
