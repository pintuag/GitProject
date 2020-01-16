package com.example.gitproject.models.repository

import com.example.gitproject.models.dataModel.TrendingListModel
import com.example.gitproject.models.httpService.ResponseHandler
import com.example.gitproject.models.httpService.Result

class DataRepository(val dataSource: DataSource) {

    fun getTrendingDataList(responseHandler: ResponseHandler<Result<List<TrendingListModel>>>) {
        dataSource.trendingDataList(responseHandler)
    }

}