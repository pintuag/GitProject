package com.example.gitproject.view.baseActivity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.gitproject.R
import com.example.gitproject.util.Constants
import com.example.gitproject.view.fragments.HomeFragment

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        init()
    }

    abstract fun getLayoutId(): Int
    abstract fun init()

    fun requestNeededPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                Constants.MY_STORAGE_PERMISSIONS_REQUEST
            )
        } else {
            addFragment(HomeFragment())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            Constants.MY_STORAGE_PERMISSIONS_REQUEST -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "Please provide necessary permissions to use the app.",
                        Toast.LENGTH_SHORT
                    ).show()
                    requestNeededPermissions()
                } else {
                    addFragment(HomeFragment())
                }
                return
            }
        }
    }

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