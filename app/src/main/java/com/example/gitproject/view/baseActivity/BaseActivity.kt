package com.example.gitproject.view.baseActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gitproject.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        init()
    }

    abstract fun getLayoutId(): Int
    abstract fun init()


    public fun AppCompatActivity.addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.mainContainer, fragment, fragment::class.java.simpleName).commit()
    }

    public fun AppCompatActivity.addFragment(fragment: Fragment, backstack: String) {
        supportFragmentManager.beginTransaction()
            .add(R.id.mainContainer, fragment, fragment::class.java.simpleName)
            .addToBackStack(backstack).commit()
    }

    public fun AppCompatActivity.replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.mainContainer, fragment, fragment::class.java.simpleName).commit()
    }

    fun AppCompatActivity.replaceFragment(fragment: Fragment, backstack: String) {
        supportFragmentManager.beginTransaction()
            .add(R.id.mainContainer, fragment, fragment::class.java.simpleName)
            .addToBackStack(backstack).commit()
    }
}