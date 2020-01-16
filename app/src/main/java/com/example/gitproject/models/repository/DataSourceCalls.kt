package com.example.gitproject.models.repository

import com.example.gitproject.models.dataModel.TrendingListModel
import com.example.gitproject.models.httpService.ResponseHandler
import com.example.gitproject.models.httpService.Result

interface DataSourceCalls {

    fun trendingDataList(responseHandler: ResponseHandler<Result<List<TrendingListModel>>>)

}