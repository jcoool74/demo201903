package com.example.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.app.tab.ActivityTabLayout;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "_MainActivity_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (false) {
            startActivity(new Intent(this, ListActivity.class));
        } else {
            startActivity(new Intent(this, ActivityTabLayout.class));
        }

    }

}
