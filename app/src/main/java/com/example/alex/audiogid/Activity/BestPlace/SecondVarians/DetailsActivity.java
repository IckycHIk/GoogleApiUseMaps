package com.example.alex.audiogid.Activity.BestPlace.SecondVarians;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.audiogid.Activity.BestPlace.SecondVarians.Util.Equations;
import com.example.alex.audiogid.Activity.BestPlace.SecondVarians.Util.Globals;
import com.example.alex.audiogid.Interfac.setMarker;
import com.example.alex.audiogid.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DetailsActivity extends AppCompatActivity {
    int position;
    Toolbar toolbar;
    private LatLng latLng;
     private Button goToGoogleMaps;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_best_places_v2);
        position = getIntent().getExtras().getInt("POSITION");
        goToGoogleMaps = findViewById(R.id.goToMap);
        final Context context = this;
goToGoogleMaps.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
intent.putExtra("Firstone",String.valueOf(Globals.LatFist[position]));
        intent.putExtra("Secondone",String.valueOf(Globals.LatSecond[position]));
        setResult(RESULT_OK,intent);
        finish();
    }
});
        setImage();
        setText();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }




    private void setText() {
        TextView txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtDescription.setText(Globals.description[position]);
        TextView txtInfo = (TextView) findViewById(R.id.InfoAboutPlace);
        txtInfo.setText(Globals.location[position]);
        TextView txtLocation = (TextView) findViewById(R.id.InfoAboutPosition);

        txtLocation.setText(String.valueOf(Globals.LatFist[position])+","+String.valueOf(Globals.LatSecond[position]));



    }

    private void setImage() {
        final ImageView image = (ImageView) findViewById(R.id.image);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Picasso.get().load(Globals.link[position]).centerCrop().resize(size.x, 1200).into(image, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                if (bitmap != null) {
                    Palette palette = Palette.from(bitmap).generate();
                    int vibrant = palette.getDarkVibrantColor(0x40000000);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(vibrant);
                    }
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.txtBack);
                    linearLayout.setBackgroundColor(Equations.getColorWithAlpha(vibrant, 0.5f));
                }
            }

            @Override
            public void onError(Exception e) {

            }


        });
    }



}
