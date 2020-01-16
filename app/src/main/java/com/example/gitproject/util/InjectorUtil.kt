package com.example.gitproject.util

import com.example.gitproject.models.repository.DataRepository
import com.example.gitproject.models.repository.DataSource


object InjectorUtil {
    fun provideHospitalRepository(): DataRepository {
        val dataSource = DataSource
        return DataRepository(dataSource)
    }
}
