package com.example.app.ui.list

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.app.BR

//import com.example.app.BR
import com.example.app.R
import com.example.app.model.JobPosting
import com.example.app.viewmodel.ViewModelEx

class ListAdapter// Provide a suitable constructor (depends on the kind of dataset)
(private val viewModel: ViewModelEx, listener: InteractionListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private val eventListener: ViewEventListener

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    inner class ViewHolderEx(// each data item is just a string in this case
            private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: Any?) {
            binding.setVariable(BR.jobPosting, obj)
            binding.executePendingBindings()
        }
    }

    private inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById(R.id.progressBar)
        }
    }

    init {
        this.eventListener = ViewEventListener(listener)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            // create a new view
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.view_holder_layout, parent, false)
            // set the view's size, margins, paddings and layout parameters
            binding.setVariable(BR.eventListener, eventListener)
            return ViewHolderEx(binding)
        } else {
            val layoutInflater = LayoutInflater.from(parent.context)
            val inflate = layoutInflater.inflate(R.layout.item_loading, parent, false)
            return LoadingViewHolder(inflate)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val list = viewModel.getList().value
        val _position = if (list != null) list[position] else null
        //Log.d(Config.TAG, "onBindViewHolder - _position: " + _position);

        if (holder is ViewHolderEx) {
            holder.bind(_position)
        } else if (holder is LoadingViewHolder) {
            showLoadingView(holder, position)
        }
    }

    private fun showLoadingView(holder: LoadingViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        val list = viewModel.getList().value
        return list?.size ?: 0
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    override fun getItemViewType(position: Int): Int {
        val list = viewModel.getList().value
        val _position = if (list != null) list[position] else null
        //Log.d(Config.TAG, "getItemViewType - _position: " + _position);
        return if (_position == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

}
