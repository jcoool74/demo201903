package com.example.app;

//import android.app.Fragment;

import android.arch.lifecycle.Observer;
import android.support.v4.app.Fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//https://guides.codepath.com/android/creating-and-using-fragments
//https://developer.android.com/guide/topics/ui/layout/recyclerview#java
public class FragmentEx extends Fragment {

    private static final String TAG = "FragmentEx";

    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ViewModelEx viewModelEx;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_layout, container, false);
        //return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModelEx = ViewModelProviders.of(this).get(ViewModelEx.class);
        viewModelEx.getList().observe(this, new Observer<List<Position>>() {
            @Override
            public void onChanged(@Nullable List<Position> temperatureData) {
                Log.d(TAG, "onChanged");
                mAdapter.notifyDataSetChanged();
            }
        });

        if (true) {
            setAdapter(view);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setAdapter(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        Position hamburg = new Position();
        hamburg.setCompany("Samsung");

        Position berlin = new Position();
        berlin.setCompany("LG");

        // define an adapter
        mAdapter = new AdapterEx(viewModelEx);
        recyclerView.setAdapter(mAdapter);

        Repo.xxx(this);
    }
}
