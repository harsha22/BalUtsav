package com.example.haatmakuri.uiexp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class SplashScr extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //To translate the image
/*
        ImageView img = (ImageView)findViewById(R.id.logo);
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, -500.0f);
        animation.setDuration(3000);  // animation duration
        animation.setRepeatCount(0);
        animation.setFillAfter(true);
        img.startAnimation(animation);
*/


            Thread timerThread = new Thread(){
            public void run(){
                try{

                    sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    Intent intent = new Intent(SplashScr.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
