package com.example.app.ui.list

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v4.app.FragmentManager
import android.databinding.DataBindingUtil

import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.app.R
import com.example.app.databinding.ActivityListBinding
import com.example.app.ui.detail.DetailActivity
import com.example.app.viewmodel.ViewModelEx
import com.example.app.viewmodel._ViewModelFactory

class ListActivity : AppCompatActivity(), InteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = _ViewModelFactory.createFactory(this)

        val binding = DataBindingUtil.setContentView<ActivityListBinding>(this, R.layout.activity_list)
        val viewModel = ViewModelProviders.of(this, factory).get(ViewModelEx::class.java)

        binding.jobPostingViewModel = viewModel
        binding.lifecycleOwner = this

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.add(R.id.fragment_container, ListFragment.newInstance("", ""))
        ft.commit()
    }

    override fun onListItemClick(id: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}
