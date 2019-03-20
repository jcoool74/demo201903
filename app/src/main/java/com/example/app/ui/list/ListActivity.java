package com.example.app.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.databinding.DataBindingUtil;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.app.R;
import com.example.app.databinding.ActivityListBinding;
import com.example.app.ui.detail.DetailActivity;
import com.example.app.viewmodel.ViewModelEx;
import com.example.app.viewmodel._ViewModelFactory;

public class ListActivity extends AppCompatActivity implements InteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _ViewModelFactory factory = _ViewModelFactory.createFactory(this);

        ActivityListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        ViewModelEx viewModel = ViewModelProviders.of(this, factory).get(ViewModelEx.class);

        binding.setJobPostingViewModel(viewModel);
        binding.setLifecycleOwner(this);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, ListFragment.newInstance("", ""));
        ft.commit();
    }

    @Override
    public void onListItemClick(String id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
