package com.nick.baac.baacrestaurant;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.logging.Handler;

public class SpaceScreen extends AppCompatActivity {

    private ImageView monkeyImageview;
    private AnimationDrawable objAnimationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_screen);

        monkeyImageview = (ImageView) findViewById(R.id.imvMonkey);
        monkeyImageview.setBackgroundResource(R.anim.monkey);
        objAnimationDrawable = (AnimationDrawable) monkeyImageview.getBackground();
        objAnimationDrawable.start();

        //auto Tread
        android.os.Handler objHandler = new android.os.Handler();
        objHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SpaceScreen.this, MainActivity.class));
                finish();
            }
        },5000);

        //Sound Effect
        MediaPlayer introPlayer = MediaPlayer.create(getBaseContext(), R.raw.intro_start_horse);
        introPlayer.start();

    } // OnCreate



} // Main Class
