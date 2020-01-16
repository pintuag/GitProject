package com.example.gitproject.app

import android.app.Application
import com.example.gitproject.util.TypeFaceUtil

class GitApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        TypeFaceUtil.overrideFont(
            applicationContext,
            "SERIF",
            "fonts/roboto_regular.ttf"
        )
    }

}