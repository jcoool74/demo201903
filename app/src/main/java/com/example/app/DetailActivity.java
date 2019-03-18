package com.example.app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.app.databinding.ActivityDetailBinding;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private PositionViewModel positionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (false) {
            setContentView(R.layout.activity_detail);
        } else {
            ActivityDetailBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
            Position position = new Position();
            position.setCompanyLogo("https://cdn-images-1.medium.com/max/800/1*pqS__vR2bkJaPAh4OHP7OQ.png");

            String string = getString(R.string.large_text);
            string = "hello world cup";

            position.setDescription(string);
            viewDataBinding.setPosition(position);
        }

//        positionViewModel = ViewModelProviders.of(this).get(PositionViewModel.class);
//        LiveData<List<Position>> positions = positionViewModel.getPositions();
//        positions.observe(this, new Observer<List<Position>>() {
//            @Override
//            public void onChanged(@Nullable List<Position> positions) {
//
//            }
//        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }
}
