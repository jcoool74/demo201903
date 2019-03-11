package com.example.app;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class AdapterEx extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private PositionViewModel positionViewModel;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolderEx extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private final ViewDataBinding binding;

        public ViewHolderEx(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Object obj) {
            binding.setVariable(BR.position, obj);
            binding.executePendingBindings();
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterEx(PositionViewModel positionViewModel) {
        this.positionViewModel = positionViewModel;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            // create a new view
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.view_holder_layout, parent, false);
            // set the view's size, margins, paddings and layout parameters
            return new ViewHolderEx(binding);
        } else {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View inflate = layoutInflater.inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(inflate);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Position _position;
        _position = positionViewModel.getPositions().getValue().get(position);

        //Log.d(Config.TAG, "onBindViewHolder - _position: " + _position);

        if (holder instanceof ViewHolderEx) {
            ((ViewHolderEx) holder).bind(_position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    private void showLoadingView(LoadingViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        int count;
        count = positionViewModel.getPositions().getValue().size();
        return count;
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        Position _position = positionViewModel.getPositions().getValue().get(position);
        //Log.d(Config.TAG, "getItemViewType - _position: " + _position);
        return _position == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

}
