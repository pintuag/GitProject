package com.example.gitproject.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitproject.models.dataModel.TrendingListModel
import com.example.gitproject.models.httpService.ResponseHandler
import com.example.gitproject.models.httpService.Result
import com.example.gitproject.models.repository.DataRepository

class TrendingListViewModel(application: Application, val dataRepository: DataRepository) :
    AndroidViewModel(application) {

    private val trendingListMutableLiveData = MutableLiveData<Result<List<TrendingListModel>>>()
    val trendingListLiveData: LiveData<Result<List<TrendingListModel>>>
        get() = trendingListMutableLiveData


    fun trendingListApiCall() {
        dataRepository.getTrendingDataList(object :
            ResponseHandler<Result<List<TrendingListModel>>> {
            override fun response(response: Result<List<TrendingListModel>>) {
                trendingListMutableLiveData.value = response
            }
        })
    }


}