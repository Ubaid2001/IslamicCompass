package com.example.islamiccompass;

import static android.app.UiModeManager.MODE_NIGHT_YES;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, CompassFragment.OnFragmentInteractionListener, DataPassListener {


    @Override
    protected void attachBaseContext(Context baseContext) {
        super.attachBaseContext(baseContext);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(baseContext);
        boolean isDark = prefs.getBoolean("IS_DARK", false);
//        System.out.println("IS_DARK: " + isDark);

        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view,
                new HomeFragment()).commit();
    }


    private BottomNavigationView.OnItemSelectedListener navListner =
            new BottomNavigationView.OnItemSelectedListener(){

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_books:
                            selectedFragment = new BooksFragment();
                            break;
                        case R.id.nav_settings:
                            selectedFragment = new SettingsFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view,
                            selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void passData(String data) {
        Fragment bookDetailsFragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        //args.putString("data_received", data);
        System.out.println("data received " + data);
        args.putString("data_received", data);
        bookDetailsFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_book_container_view, bookDetailsFragment )
                .commit();
    }

    public void passPref(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        System.out.println("IS_DARK: " + pref.getBoolean("IS_DARK", false));
        boolean isDark = pref.getBoolean("IS_DARK", false);
        if(isDark){
            System.out.println("Night Mode ------> Light Mode");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }else {
            System.out.println("Light Mode ------> Night Mode");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


    @Override
    public void messageFromChildFragment(Uri uri) {

    }

    @Override
    public void messageFromParentFragment(Uri uri) {

    }

//    @Override
//    public void onFragmentInteraction(Uri uri){
//        //you can leave it empty
//    }

}
