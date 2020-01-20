package com.example.gitproject.models.httpService

import com.example.gitproject.models.dataModel.BitmapModel
import com.example.gitproject.models.dataModel.TrendingListModel
import com.example.gitproject.util.ApiConstants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Api {

    /*
    * Github Data Api
    * */
    @GET(ApiConstants.GITHUB_API)
    fun getGithubTrendingList(@QueryMap params: Map<String, String>): Observable<List<TrendingListModel>>

}