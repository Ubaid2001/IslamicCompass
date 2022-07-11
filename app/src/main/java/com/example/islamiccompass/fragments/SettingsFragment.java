package com.example.islamiccompass.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.islamiccompass.helper.DataPassListener;
import com.example.islamiccompass.R;

public class SettingsFragment extends Fragment {

    View view;
    private ImageView gifImage;
    private TextView darkmode;
    private Button lightSwitch;
    SharedPreferences sharedPreferences = null;

    DataPassListener mCallback;




    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try
        {
            mCallback = (DataPassListener) context;

        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+ " must implement DataPassListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        gifImage = (ImageView) view.findViewById(R.id.gifImage);
        darkmode = (TextView) view.findViewById(R.id.darkmode);
        lightSwitch = (Button) view.findViewById(R.id.lightSwitch);

        lightSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = preferences.edit();
                if(Configuration.UI_MODE_NIGHT_NO == currentNightMode) {
                    System.out.println("Onclick Set Boolean To IS_LIGHT - True");
                    editor.putBoolean("IS_DARK", false);
                    editor.apply();
                } else {
                    System.out.println("Onclick Set Boolean To IS_DARK - True");
                    editor.putBoolean("IS_DARK", true);
                    editor.apply();
                }
                mCallback.passPref();
            }
        });

//        int s1 = R.drawable.sallahasdeenvol1;
//        int s2 = R.drawable.sallahasdeenvol2;
//        int s3 = R.drawable.sallahasdeenvol3;
//        int a1 = R.drawable.abubakerassiddeeq;
//        int u1 = R.drawable.umaribnalkhattabvol1;
//        int u2 = R.drawable.umaribnalkhattabvol2;

//        System.out.println("s1: " + s1 + " s2: " + s2 + " s3: " + s3 +
//                " a1: " + a1 + " u1: " + u1 + " u2: " + u2);

        return view;
    }
}