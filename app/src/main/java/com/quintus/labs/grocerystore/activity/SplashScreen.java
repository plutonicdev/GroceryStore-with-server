package com.quintus.labs.grocerystore.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.quintus.labs.grocerystore.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over


                    Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }


        }, 3000);
    }
}