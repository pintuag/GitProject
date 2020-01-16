package com.example.gitproject.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gitproject.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        openMainActivity()
    }

    private fun openMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }
}
