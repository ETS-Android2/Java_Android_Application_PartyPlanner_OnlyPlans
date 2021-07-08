package com.example.partyplaner;

//  FILE HEADER COMMENT:
//  FILE            : SplashScreen.java
//  PROJECT         : A-02 : Mobile App Party Planner - OnlyPlans (PROG3150)
//  FIRST VERSION   : 2021-02-13 (Rev.01)
//  AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//  DESCRIPTION     : This is the class for the SplashScreen that starts when the
//                    app opens displaying an image then loading the main activity.

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splashscreen);

        //Set delay
        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //Open MainActivity
                startActivity(new Intent(SplashScreen.this,
                        MainActivity.class));
                finish();
            }
        }, secondsDelayed * 1000);
    }
}
