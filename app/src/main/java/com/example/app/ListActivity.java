package com.example.app;

import android.support.v4.app.FragmentManager;
import android.databinding.DataBindingUtil;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.app.databinding.ActivityListBinding;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (false) {
            setContentView(R.layout.activity_list);
        } else {
            ActivityListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
            Position position = new Position();
            binding.setPosition(position);
        }

        test();
    }

    private void test() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, new FragmentEx());
        ft.commit();
    }
}
