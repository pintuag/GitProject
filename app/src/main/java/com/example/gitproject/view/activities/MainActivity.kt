package com.example.gitproject.view.activities

import com.example.gitproject.R
import com.example.gitproject.view.baseActivity.BaseActivity
import com.example.gitproject.view.fragments.HomeFragment
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_main

    override fun init() {
        setToolbar()
        requestNeededPermissions()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowCustomEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


}
