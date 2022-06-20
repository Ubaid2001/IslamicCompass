package com.example.islamiccompass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;


///**
// * A simple {@link Fragment} subclass.
// * Use the {@link SplashScreen#newInstance} factory method to
// * create an instance of this fragment.
// */
public class SplashScreen extends AppCompatActivity {
    private LottieAnimationView lottie_main;
    private RelativeLayout splashscreen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean isDark = prefs.getBoolean("IS_DARK", false);
        System.out.println("IS_DARK: " + isDark);
        lottie_main = (LottieAnimationView) findViewById(R.id.lottie_main);
        splashscreen = (RelativeLayout) findViewById(R.id.splashcreen);

        //The Activity turn the app to light when the isDark is true
        //Due to this the Ripple.json will be set when isDark is true
        if(isDark) {
            lottie_main.setAnimation("Ripple.json");
            splashscreen.setBackgroundColor(Color.parseColor("#C3C3C3"));
        }else{
            lottie_main.setAnimation("Ripplewhite.json");
            splashscreen.setBackgroundColor(Color.parseColor("#2C2B2B"));
        }


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }, 2000);

    }
}