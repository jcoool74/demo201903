package com.example.app.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View

import com.example.app.R
import com.example.app.databinding.ActivityDetailBinding
import com.example.app.model.JobPosting
import com.example.app.viewmodel.ViewModelEx
import com.example.app.viewmodel._ViewModelFactory

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getStringExtra("id")

        val factory = _ViewModelFactory.createFactory(this)
        val viewDataBinding = DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
        val viewModel = ViewModelProviders.of(this, factory).get(ViewModelEx::class.java)

        viewModel.getOne(id).observe(this, Observer { _jobPosting ->
            Log.d(TAG, "onChanged: " + _jobPosting!!)
            if (_jobPosting == null) return@Observer
            Log.d(TAG, "onChanged: " + _jobPosting.title!!)
            supportActionBar!!.title = _jobPosting.title
        })

        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = this

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""
    }

    private fun initFab() {
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        fab.visibility = View.GONE
    }

    companion object {
        private val TAG = DetailActivity::class.java.simpleName
    }
}
