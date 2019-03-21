package com.example.app.ui.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.app.R;
import com.example.app.databinding.ActivityDetailBinding;
import com.example.app.model.JobPosting;
import com.example.app.viewmodel.ViewModelEx;
import com.example.app.viewmodel._ViewModelFactory;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String id = getIntent().getStringExtra("id");

        _ViewModelFactory factory = _ViewModelFactory.createFactory(this);
        ActivityDetailBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        ViewModelEx viewModel = ViewModelProviders.of(this, factory).get(ViewModelEx.class);

        viewModel.getOne(id).observe(this, new Observer<JobPosting>() {
            @Override
            public void onChanged(@Nullable JobPosting _jobPosting) {
                Log.d(TAG, "onChanged: " + _jobPosting);
                if (_jobPosting == null) return;
                viewDataBinding.setJobPosting(_jobPosting);
            }
        });

        Log.d(TAG, "getSupportActionBar: " + getSupportActionBar());
    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);
    }
}
