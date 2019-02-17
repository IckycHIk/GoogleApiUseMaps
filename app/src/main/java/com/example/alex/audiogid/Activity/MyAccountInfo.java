package com.example.alex.audiogid.Activity;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.audiogid.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OSUtils;
import com.gyf.barlibrary.OnKeyboardListener;
import com.jgabrielfreitas.core.BlurImageView;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrListener;
import com.r0adkll.slidr.model.SlidrPosition;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yinglan.shadowimageview.ShadowImageView;

import jp.wasabeef.blurry.Blurry;

import static android.view.Gravity.BOTTOM;
import static android.view.Gravity.RIGHT;
import static android.view.Gravity.TOP;
import static android.widget.GridLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;

public class MyAccountInfo extends AppCompatActivity {
private SlidrConfig config;
private TextView userName;
private  TextView userMail;
private ShadowImageView shadowImageView;
    private FirebaseAuth mAuth;
    private BlurImageView blurImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_account_info);

        userName = findViewById(R.id.UserNameInfo);
        userMail = findViewById(R.id.UserMailInfo);


        shadowImageView = findViewById(R.id.userPictures);
blurImageView = findViewById(R.id.BlurImageView);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        userName.setText("Name:     "+user.getDisplayName());
        userMail.setText("Mail:     "+user.getEmail());
        shadowImageView.setImageResource(R.drawable.bbbb);
        blurImageView.setBlur(10);

        // in Activity Context
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.icons8_xbox_menu_128);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
              //  .addSubActionView(button1)
                //   .addSubActionView(button2)
                // ...
                .attachTo(actionButton)
                .build();

        /*
        String imageUrl ="https://firebasestorage.googleapis.com/v0/b/maps-2019-2f343.appspot.com/o/Unknown.jpg?alt=media&token=5047e6be-7d09-4037-85be-4237d480a46d";


        Picasso.get().load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                shadowImageView.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
*/

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

