package com.example.gitproject.models.repository

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitproject.util.InjectorUtil
import com.example.gitproject.viewModels.TrendingListViewModel

// Factory class for creating viewmodels used in whole application
class ViewModelFactory private constructor(
    val mApplication: Application,
    val dataRepository: DataRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrendingListViewModel::class.java)) {
            return TrendingListViewModel(mApplication, dataRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        // creates single object for ViewModelFactory through singleton pattern
        fun getInstance(application: Application): ViewModelFactory? {

            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            ViewModelFactory(application, InjectorUtil.provideHospitalRepository())
                    }
                }
            }
            return INSTANCE
        }
    }

}
