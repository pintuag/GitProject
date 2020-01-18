package com.example.gitproject.models.repository

import com.example.gitproject.models.dataModel.TrendingListModel
import com.example.gitproject.models.httpService.ResponseHandler
import com.example.gitproject.models.httpService.Result
import com.example.gitproject.models.httpService.ServiceCreator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException

object DataSource : DataSourceCalls {


    override fun trendingDataList(
        params: HashMap<String, String>,
        responseHandler: ResponseHandler<Result<List<TrendingListModel>>>
    ) {
        responseHandler.response(Result.Loading)
        val service = ServiceCreator.createService()
        val call = service.getGithubTrendingList(params)
        call.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    responseHandler.response(Result.Success(it))
                },
                {
                    responseHandler.response(Result.Error(Exception(exceptionErrors(it))))
                }
            )
    }


    fun exceptionErrors(throwable: Throwable): String {
        if (throwable is IOException) {
            // A network or conversion error happened
            return "Network Error"
        }
        if (throwable is IllegalStateException) {
            // data parsing error
            return "Data Parsing Error"
        }
        // any other network call exception
        return "Please try again Later"
    }

}