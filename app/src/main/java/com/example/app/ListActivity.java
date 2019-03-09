package com.example.app;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.app.databinding.ActivityListBinding;
import com.vogella.android.databinding.TemperatureData;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (false) {
            setContentView(R.layout.activity_list);
        } else {
            ActivityListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
            TemperatureData temperatureData = new TemperatureData("Hamburg", "10");
            binding.setTemp(temperatureData);
        }

        test();
    }

    private void test() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, new YourFragment());
        ft.commit();
    }
}
