package com.example.alex.audiogid.Activity.BestPlace;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.example.alex.audiogid.Activity.BestPlace.utils.DecodeBitmapTask;
import com.example.alex.audiogid.R;


public class DetailsActivity extends AppCompatActivity implements DecodeBitmapTask.Listener {

    static final String BUNDLE_IMAGE_ID = "BUNDLE_IMAGE_ID";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final int smallResId = getIntent().getIntExtra(BUNDLE_IMAGE_ID, -1);
        if (smallResId == -1) {
            finish();
            return;
        }

        imageView = (ImageView)findViewById(R.id.image);
        imageView.setImageResource(smallResId);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailsActivity.super.onBackPressed();
            }
        });

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

        } else {
            getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {

                private boolean isClosing = false;

                @Override
                public void onTransitionPause(Transition transition) {}
                @Override
                public void onTransitionResume(Transition transition) {}
                @Override
                public void onTransitionCancel(Transition transition) {}

                @Override
                public void onTransitionStart(Transition transition) {
                    if (isClosing) {
                        addCardCorners();
                    }
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    if (!isClosing) {
                        isClosing = true;

                        removeCardCorners();

                    }
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    private void addCardCorners() {
        final CardView cardView = (CardView) findViewById(R.id.card);
        cardView.setRadius(25f);
    }

    private void removeCardCorners() {
        final CardView cardView = (CardView)findViewById(R.id.card);
        ObjectAnimator.ofFloat(cardView, "radius", 0f).setDuration(50).start();
    }



    @Override
    public void onPostExecuted(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

}
