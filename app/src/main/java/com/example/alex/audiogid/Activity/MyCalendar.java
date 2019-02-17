package com.example.alex.audiogid.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.alex.audiogid.R;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrListener;
import com.r0adkll.slidr.model.SlidrPosition;

public class MyCalendar extends AppCompatActivity {
    private SlidrConfig config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar);

        getWindow().setBackgroundDrawableResource(R.color.transparent  );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);

        config = new SlidrConfig.Builder()
                .primaryColor(getResources().getColor(R.color.colorPrimary))
                .secondaryColor(getResources().getColor(R.color.blue))
                .position(SlidrPosition.HORIZONTAL)
                //|RIGHT|TOP|BOTTOM|VERTICAL|HORIZONTAL
                .sensitivity(1f)
                .scrimColor(Color.BLACK)
                .scrimStartAlpha(0.8f)
                .scrimEndAlpha(0f)
                .velocityThreshold(2400)
                .distanceThreshold(0.25f)
                .edge(true|false)
                .edgeSize(0.18f) // The % of the screen that counts as the edge, default 18%
                .listener(new SlidrListener(){
                    @Override
                    public void onSlideStateChanged(int state) {

                    }

                    @Override
                    public void onSlideChange(float percent) {

                    }

                    @Override
                    public void onSlideOpened() {

                    }

                    @Override
                    public void onSlideClosed() {

                    }
                })
                .build();


        Slidr.attach(this);
    }
}
