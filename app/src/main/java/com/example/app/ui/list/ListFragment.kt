package com.example.app.ui.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.app.R
import com.example.app.model.JobPosting
import com.example.app.util.Config
import com.example.app.viewmodel.ViewModelEx

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [InteractionListener] interface
 * to handle interaction events.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
//https://guides.codepath.com/android/creating-and-using-fragments
//https://developer.android.com/guide/topics/ui/layout/recyclerview#java
class ListFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: InteractionListener? = null

    private var mView: View? = null
    private var recyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var viewModel: ViewModelEx? = null
    private var lastItem: Int = 0
    private var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.mView = inflater.inflate(R.layout.fragment_layout, container, false)
        //return super.onCreateView(inflater, container, savedInstanceState);
        return mView
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initRecyclerView(view)
        initScrollListener()

        viewModel!!.loadMoreList(0)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is InteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement InteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById<View>(R.id.my_recycler_view) as RecyclerView

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView!!.setHasFixedSize(true)

        // use a linear layout manager
        layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager

        // define an adapter
        viewModel?.let {
            val viewModelEx = it
            mListener?.let {
                mAdapter = ListAdapter(viewModelEx, it)
                recyclerView!!.adapter = mAdapter
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(activity!!).get(ViewModelEx::class.java)

        val positions = viewModel!!.getList()
        positions.observe(this, Observer { list ->
            Log.d(Config.TAG, "onChanged")
            lastItem = list!!.size - 1
            mAdapter!!.notifyDataSetChanged()
            isLoading = false
        })
    }

    private fun initScrollListener() {
        //https://www.journaldev.com/24041/android-recyclerview-load-more-endless-scrolling
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //Log.d(Config.TAG, "onScrolled: " + (lastItem + 1));

                if (!isLoading) {
                    val size = viewModel!!.getList().value!!.size
                    val linearLayoutManager = recyclerView!!.layoutManager as LinearLayoutManager

                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == size - 1) {
                        //bottom of list!
                        recyclerView.post { loadMore() }
                        //                        loadMore();
                        //                        isLoading = true;
                    }
                }

            }
        })
    }

    private fun loadMore() {
        if (Config.ADD_NULL_PROGRESS) {
            val value = viewModel!!.getList().value!! as MutableList<JobPosting>
            value.add(JobPosting())
            //value.add(null)
            mAdapter!!.notifyItemInserted(viewModel!!.getList().value!!.size - 1)
        }

        if (!isLoading) {
            Log.d(Config.TAG, "loadMore - loadMore: " + (lastItem + 1))
            viewModel!!.loadMoreList(lastItem + 1)
            isLoading = true
        }
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        private val TAG = "FragmentEx"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): ListFragment {
            val fragment = ListFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
