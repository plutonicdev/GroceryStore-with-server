package com.quintus.labs.grocerystore.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.gson.Gson;
import com.quintus.labs.grocerystore.R;
import com.quintus.labs.grocerystore.model.User;
import com.quintus.labs.grocerystore.util.localstorage.LocalStorage;

public class SplashScreen extends AppCompatActivity {

    LocalStorage localStorage;
    Gson gson = new Gson();
    String userjson;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                localStorage = new LocalStorage(getApplicationContext());
                if (localStorage.isUserLoggedIn()) {
                    userjson = localStorage.getUserLogin();
                    user = gson.fromJson(userjson, User.class);
                    if (user.isPhone_verified()) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(getApplicationContext(), LoginRegisterActivity.class));
                        finish();
                    }
                } else {
                    Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }



            }


        }, 3000);
    }
}