package com.example.gitproject.models.repository

import com.example.gitproject.models.dataModel.TrendingListModel
import com.example.gitproject.models.httpService.ResponseHandler
import com.example.gitproject.models.httpService.Result
import com.example.gitproject.models.httpService.ServiceCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

object DataSource : DataSourceCalls {


    override fun trendingDataList(responseHandler: ResponseHandler<Result<List<TrendingListModel>>>) {
        responseHandler.response(Result.Loading)
        val service = ServiceCreator.createService()
        val call = service.getGithubTrendingList()
        call.enqueue(object : Callback<List<TrendingListModel>> {
            override fun onFailure(call: Call<List<TrendingListModel>>, t: Throwable) {
                t.printStackTrace()
                responseHandler.response(Result.Error(Exception(exceptionErrors(t))))
            }

            override fun onResponse(
                call: Call<List<TrendingListModel>>,
                response: Response<List<TrendingListModel>>
            ) {

                if (response.isSuccessful) {
                    responseHandler.response(Result.Success(response.body()!!))
                } else {
                    responseHandler.response(Result.Error(Exception("Data Not Found")))
                }
            }
        })

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