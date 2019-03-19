package com.example.app.apple;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.app.R;
import com.example.app.databinding.ActivityAppleBinding;
import com.example.app.databinding.ActivityDetailBinding;

public class AppleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAppleBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_apple);
        binding.setLifecycleOwner(this);

        AppleViewModel appleViewModel = ViewModelProviders.of(this).get(AppleViewModel.class);
        binding.setApple(appleViewModel);
    }
}
