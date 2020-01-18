package com.example.gitproject.models.repository

import com.example.gitproject.models.dataModel.TrendingListModel
import com.example.gitproject.models.httpService.ResponseHandler
import com.example.gitproject.models.httpService.Result

class DataRepository(val dataSource: DataSource) {

    fun getTrendingDataList(
        params: HashMap<String, String>,
        responseHandler: ResponseHandler<Result<List<TrendingListModel>>>
    ) {
        dataSource.trendingDataList(params, responseHandler)
    }

}