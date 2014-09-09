package com.wuqi.jobnow.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.wuqi.jobnow.R;

import java.util.Timer;
import java.util.TimerTask;


public class WelcomeActivity extends Activity {

    private ImageSwitcher imageSwitcher;

    private int[] gallery = {R.drawable.background1, R.drawable.background4, R.drawable.background2,};

    private int position = 0;

    private Timer timer = null;

    View i;

    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Action Bar Changes
        setTitle("");
        getActionBar().setIcon(R.drawable.logo);


        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );

        Button button = (Button)findViewById(R.id.button);
        button.setTypeface(font);
        imageSwitcher = (ImageSwitcher)findViewById(R.id.imageSwitcher);

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory(){

            public View makeView()
            {
                i = new ImageView(WelcomeActivity.this);
                i.setLayoutParams(params);
                i.setScaleX((float) 1.070);
                i.setScaleY((float) 1.070);
                return i;
            }
        });

            // Set animations
            Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

            imageSwitcher.setInAnimation(fadeIn);
            imageSwitcher.setOutAnimation(fadeOut);
            //Start timeset transition
            start();

    }


        public void goToLogin(View view){
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }




    //Set time between changing background
    public void start()
    {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                // avoid exception: "Only the original thread that created a view hierarchy can touch its views"
                runOnUiThread(new Runnable() {
                    public void run() {
                        imageSwitcher.setImageResource(gallery[position]);
                        position++;
                        if (position == 3)
                        {
                            position = 0;
                        }
                    }
                });
            }
        }, 0, 5500);
    }

}
