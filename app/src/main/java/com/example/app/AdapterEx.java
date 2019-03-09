package com.example.app;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class AdapterEx extends RecyclerView.Adapter<AdapterEx.ViewHolderEx> {
    private ViewModelEx viewModelEx;


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

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterEx(ViewModelEx viewModelEx) {
        this.viewModelEx = viewModelEx;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolderEx onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.view_holder_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolderEx(binding);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolderEx holder, int position) {
        Position _position = null;

            _position = viewModelEx.getList().getValue().get(position);


        holder.bind(_position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        int count;

            count = viewModelEx.getList().getValue().size();

        //return data.size();
        return count;
    }

}
