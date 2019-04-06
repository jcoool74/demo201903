package com.example.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.app.ApplicationExt;
import com.example.app.MyExample;
import com.example.app.R;
import com.example.app.ui.detail.DetailActivity;
import com.example.app.ui.list.ListActivity;
import com.example.app.ui.tab.ActivityTabLayout;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    @Inject
    MyExample myExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((ApplicationExt) getApplication())
                .getMyComponent()
                .inject(MainActivity.this);

        Log.d(TAG, "msldk: " + myExample.getDate());

        if (true) {
            if (true) {
                startActivity(new Intent(this, ListActivity.class));
            } else {
            }
        } else {
            if (false) {
                startActivity(new Intent(this, ActivityTabLayout.class));
            } else {
                if (false) {
                    startActivity(new Intent(this, DetailActivity.class));
                } else {
                }
            }
        }
    }
}
