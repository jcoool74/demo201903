package com.example.app;

//import android.app.Fragment;

import android.arch.lifecycle.LiveData;
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

import java.util.List;

//https://guides.codepath.com/android/creating-and-using-fragments
//https://developer.android.com/guide/topics/ui/layout/recyclerview#java
public class FragmentEx extends Fragment {

    private static final String TAG = "FragmentEx";

    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private PositionViewModel positionViewModel;
    private int lastItem;
    private boolean isLoading;

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

        initViewModel();
        initRecyclerView(view);
        initScrollListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // define an adapter
        mAdapter = new AdapterEx(positionViewModel);
        recyclerView.setAdapter(mAdapter);
    }

    private void initViewModel() {
        positionViewModel = ViewModelProviders.of(this).get(PositionViewModel.class);

        LiveData<List<Position>> positions = positionViewModel.getPositions(lastItem);
        positions.observe(this, new Observer<List<Position>>() {
            @Override
            public void onChanged(@Nullable List<Position> list) {
                Log.d(Config.TAG, "onChanged");
                lastItem = list.size() - 1;
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initScrollListener() {
        //https://www.journaldev.com/24041/android-recyclerview-load-more-endless-scrolling
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //Log.d(Config.TAG, "onScrolled: " + (lastItem + 1));

                if (!isLoading) {
                    int size = positionViewModel.getMutableLiveData().getValue().size();
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == size - 1) {
                        //bottom of list!
                        loadMore();
//                        isLoading = true;
                    }
                }

            }
        });
    }

    private void loadMore() {
        if (Config.ADD_NULL_PROGRESS) {
            positionViewModel.getMutableLiveData().getValue().add(null);
            mAdapter.notifyItemInserted(positionViewModel.getMutableLiveData().getValue().size() - 1);
        }

        Log.d(Config.TAG, "loadMore - loadMore: " + (lastItem + 1));
        positionViewModel.getPositions(lastItem + 1);
    }
}