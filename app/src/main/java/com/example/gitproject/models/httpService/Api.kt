package com.example.gitproject.models.httpService

import com.example.gitproject.models.dataModel.TrendingListModel
import com.example.gitproject.util.ApiConstants
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    /*
    * Github Data Api
    * */
    @GET(ApiConstants.GITHUB_API)
    fun getGithubTrendingList(): Call<List<TrendingListModel>>

}