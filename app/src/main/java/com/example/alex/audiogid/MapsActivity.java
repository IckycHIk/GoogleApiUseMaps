package com.example.alex.audiogid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.codemonkeylabs.fpslibrary.FrameDataCallback;
import com.codemonkeylabs.fpslibrary.TinyDancer;
import com.example.alex.audiogid.Activity.BestPlace.BestPlace;
import com.example.alex.audiogid.Activity.BestPlace.SecondVarians.BestPlacesV2;
import com.example.alex.audiogid.Activity.MyAccountInfo;
import com.example.alex.audiogid.Activity.MyCalendar;
import com.example.alex.audiogid.Activity.MyPurse;
import com.example.alex.audiogid.Activity.MyRoads;
import com.example.alex.audiogid.Activity.MyStatistics;
import com.example.alex.audiogid.BackClass.Adapter.DrawerRecyclerView;
import com.example.alex.audiogid.BackClass.DrawerMenuItem;
import com.example.alex.audiogid.Fragment.GoogleMaps;
import com.example.alex.audiogid.Interfac.setMarker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.konifar.fab_transformation.FabTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cat.ereza.customactivityoncrash.config.CaocConfig;
import de.hdodenhof.circleimageview.CircleImageView;

public class MapsActivity extends FragmentActivity
{
    private  FloatingSearchView mSearchView;
    private Button button;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
private FloatingActionButton fab;
private Toolbar toolbarFooter;
    private RecyclerView recyclerView;
    private List<DrawerMenuItem> data;
    private   DrawerRecyclerView adapter;


private  RecyclerView.LayoutManager manager;
   private FirebaseAuth mAuth;
private CircleImageView circleImageView;
private TextView NameOfUser;
private Context context;
    private StorageReference mStorageRef;

private setMarker setMarker;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        try {
            setMarker = (setMarker) fragment;
        } catch (ClassCastException castException) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_maps_old_version);
        toolbarFooter = findViewById(R.id.toolbar_footer);
        fab = findViewById(R.id.fab);

drawerLayout =findViewById(R.id.drawer_layout);

navigationView=findViewById(R.id.nav_view);






NameOfUser =navigationView.getHeaderView(0).findViewById(R.id.UserName);
        circleImageView = navigationView.getHeaderView(0).findViewById(R.id.profile_image);
        recyclerView = navigationView.getHeaderView(0).findViewById(R.id.drawerRecyclerView);

context = this;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();



//       String imageUrl = user.getPhotoUrl().toString();

    //  Picasso.get().load(imageUrl).into(circleImageView);
     // NameOfUser.setText(user.getDisplayName());




        button = findViewById(R.id.openDrawer);
manager = new LinearLayoutManager(this);





        CaocConfig.Builder.create()
                .errorDrawable(R.drawable.error) //default: bug image
                .restartActivity(MapsActivity.class) //default: null (your app's launch activity)
                .apply();

        ///FloatingActionButton
       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FabTransformation.with(fab)
                       .transformTo(toolbarFooter);

                       new CountDownTimer(5000, 1000) {

                           //Здесь обновляем текст счетчика обратного отсчета с каждой секундой
                           public void onTick(long millisUntilFinished) {
                           }
                           //Задаем действия после завершения отсчета (высвечиваем надпись "Бабах!"):
                           public void onFinish() {
                               FabTransformation.with(fab)
                                       .transformFrom(toolbarFooter);
                           }
                       }
                               .start();
               }

       });

       toolbarFooter.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FabTransformation.with(fab)
                       .transformFrom(toolbarFooter);
           }
       });

    ///DrawerLayout


        //initToolbar();
        initRecyclerView();
       initNavigationView();

        //Button
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initActivity(MyAccountInfo.class);
                drawerLayout.closeDrawers();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FabTransformation.with(fab)
                        .transformFrom(toolbarFooter);
                drawerLayout.openDrawer(Gravity.START);

            }
        });
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

       //initElement
iniFPSINFO();

        initFragment(new GoogleMaps(),R.id.fragment_coordinator);

    }

    private void iniFPSINFO() {
        TinyDancer.create()
                .show(context);
        TinyDancer.create()
                .redFlagPercentage(.1f) // set red indicator for 10%....different from default
                .startingXPosition(200)
                .startingYPosition(600)
                .show(context);
        TinyDancer.create()
                .addFrameDataCallback(new FrameDataCallback() {
                    @Override
                    public void doFrame(long previousFrameNS, long currentFrameNS, int droppedFrames) {
                        //collect your stats here
                    }
                })
                .show(context);

    }

    private void initRecyclerView() {

        data = new ArrayList<>();
        data.add(new DrawerMenuItem(2,"Мои Маршруты",R.drawable.icons8_gps_filled_500));
        data.add(new DrawerMenuItem(3,"Мой Календарь",R.drawable.icons8_schedule_100));
        data.add(new DrawerMenuItem(4,"Мой кошелек",R.drawable.icons8_money_bag_100));
        data.add(new DrawerMenuItem(5,"Моя Статистика°",R.drawable.icons8_statistics_100));
        data.add(new DrawerMenuItem(6,"Лучшие Места",R.drawable.icons8_trophy_100));


        adapter = new DrawerRecyclerView(data, new DrawerRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(final DrawerMenuItem item) {
                switch (item.getId()) {
                    case 2:
                        initActivity(MyRoads.class);
                        drawerLayout.closeDrawers();
                        break;
                    case 3:
                        initActivity(MyCalendar.class);
                        drawerLayout.closeDrawers();
                        break;
                    case 4:
                        initActivity(MyPurse.class);
                        drawerLayout.closeDrawers();
                        break;
                    case 5:
                        initActivity(MyStatistics.class);
                        drawerLayout.closeDrawers();
                        break;
                    case 6:

                        initActivityForResult(BestPlacesV2.class,1);
                        drawerLayout.closeDrawers();
                        break;
                }



            }
        }, new RippleView.OnRippleCompleteListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onComplete(final RippleView rippleView) {
            drawerLayout.closeDrawers();
            }
        });

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }

    private void initNavigationView() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.view_navigation_open, R.string.view_navigation_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.MyRouds: drawerLayout.closeDrawers();
                        initActivity(MyRoads.class);
                        break;
                    case R.id.MyCalendars:
                        drawerLayout.closeDrawers();
                        initActivity(MyCalendar.class);
                        break;
                    case R.id.MyPurse:
                        drawerLayout.closeDrawers();
                        initActivity(MyPurse.class);
                        break;
                    case R.id.MyStatistic:
                        drawerLayout.closeDrawers();
                        initActivity(MyStatistics.class);
                        break;
                    case R.id.BestPlaces:
                        initActivityForResult(BestPlacesV2.class,1);
                        drawerLayout.closeDrawers();

                        break;
                }
                return true;
            }
        });

    }


    private  void initActivity(Class activity){
    Intent intent = new Intent(MapsActivity.this, activity);
    startActivity(intent);

}
    private  void initActivityForResult(Class activity,int requstCode){
        Intent intent = new Intent(MapsActivity.this, activity);
       startActivityForResult(intent,requstCode);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

if(requestCode==1){

    if(resultCode==Activity.RESULT_OK){

       double Lat,LAng;

      Lat= Double.parseDouble(data.getStringExtra("First"));
        LAng= Double.parseDouble(data.getStringExtra("Second"));
        setMarker.getPoint(Lat,LAng);
    }

}

    }

    private void initFragment(Fragment fragment, int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
               .replace(id,fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (fab.getVisibility() != View.VISIBLE) {
            FabTransformation.with(fab).transformFrom(toolbarFooter);
            return;
        }
        super.onBackPressed();
    }


}
