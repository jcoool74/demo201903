package com.example.app.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.R;
import com.example.app.model.JobPosting;
import com.example.app.util.Config;
import com.example.app.viewmodel.ViewModelEx;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//https://guides.codepath.com/android/creating-and-using-fragments
//https://developer.android.com/guide/topics/ui/layout/recyclerview#java
public class ListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private InteractionListener mListener;

    private static final String TAG = "FragmentEx";

    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ViewModelEx viewModel;
    private int lastItem;
    private boolean isLoading;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InteractionListener) {
            mListener = (InteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        mAdapter = new ListAdapter(viewModel, mListener);
        recyclerView.setAdapter(mAdapter);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(getActivity()).get(ViewModelEx.class);

        LiveData<List<JobPosting>> positions = viewModel.getList();
        positions.observe(this, new Observer<List<JobPosting>>() {
            @Override
            public void onChanged(@Nullable List<JobPosting> list) {
                Log.d(Config.TAG, "onChanged");
                lastItem = list.size() - 1;
                mAdapter.notifyDataSetChanged();
                isLoading = false;
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
                    int size = viewModel.getList().getValue().size();
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == size - 1) {
                        //bottom of list!
                        recyclerView.post(() -> {
                            loadMore();
                        });
//                        loadMore();
//                        isLoading = true;
                    }
                }

            }
        });
    }

    private void loadMore() {
        if (Config.ADD_NULL_PROGRESS) {
            viewModel.getList().getValue().add(null);
            mAdapter.notifyItemInserted(viewModel.getList().getValue().size() - 1);
        }

        if (!isLoading) {
            Log.d(Config.TAG, "loadMore - loadMore: " + (lastItem + 1));
            viewModel.loadMoreList(lastItem + 1);
            isLoading = true;
        }
    }
}
