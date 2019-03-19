package com.example.app.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.app.Config;
import com.example.app.R;
import com.example.app.databinding.ActivityTestBinding;
import com.example.app.model.JobPosting;
import com.example.app.view.ViewModelEx;
import com.example.app.view._ViewModelFactory;

import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _ViewModelFactory factory = _ViewModelFactory.createFactory(this);

        ActivityTestBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        ViewModelEx viewModel = ViewModelProviders.of(this, factory).get(ViewModelEx.class);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.getList().observe(this, it -> {
            Log.d(Config.TAG, "sldjflskflsf");
        });

    }

}
