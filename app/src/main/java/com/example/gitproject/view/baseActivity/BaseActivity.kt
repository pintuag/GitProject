package com.example.gitproject.view.baseActivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gitproject.R
import com.example.gitproject.util.Constants

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
            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
            .add(R.id.mainContainer, fragment, fragment::class.java.simpleName)
            .addToBackStack(backstack).commit()
    }


    override fun onSupportNavigateUp(): Boolean {

        if (Constants.TOOLBAR_BUTTON_CLICK) {
            onBackPressed()
        }

        return true
    }


}