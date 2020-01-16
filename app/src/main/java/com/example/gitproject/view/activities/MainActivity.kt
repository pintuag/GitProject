package com.example.gitproject.view.activities

import com.example.gitproject.R
import com.example.gitproject.view.baseActivity.BaseActivity
import com.example.gitproject.view.fragments.HomeFragment

class MainActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_main

    override fun init() {
        addFragment(HomeFragment())
    }


}
