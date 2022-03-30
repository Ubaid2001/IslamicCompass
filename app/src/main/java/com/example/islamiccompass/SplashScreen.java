package com.example.islamiccompass;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.os.Looper;


///**
// * A simple {@link Fragment} subclass.
// * Use the {@link SplashScreen#newInstance} factory method to
// * create an instance of this fragment.
// */
public class SplashScreen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
//        startActivity(new Intent(this, MainActivity.class));

//        new Handler().postDelayed(() -> {
//            startActivity(new Intent(SplashScreen.this, MainActivity.class));
//            finish();
//        },2000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }, 3000);

    }
}