package com.example.app.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import com.example.app.ApplicationExt
import com.example.app.MyExample
import com.example.app.R
import com.example.app.ui.detail.DetailActivity
import com.example.app.ui.list.ListActivity
import com.example.app.ui.tab.ActivityTabLayout

import javax.inject.Inject

class MainActivity : AppCompatActivity() {
//    @Inject
    internal var myExample: MyExample? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        (application as ApplicationExt)
//                .myComponent!!
//                .inject(this@MainActivity)

//        Log.d(TAG, "msldk: " + myExample!!.date)

        if (true) {
            if (true) {
                startActivity(Intent(this, ListActivity::class.java))
            } else {
            }
        } else {
            if (false) {
                startActivity(Intent(this, ActivityTabLayout::class.java))
            } else {
                if (false) {
                    startActivity(Intent(this, DetailActivity::class.java))
                } else {
                }
            }
        }
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }
}
