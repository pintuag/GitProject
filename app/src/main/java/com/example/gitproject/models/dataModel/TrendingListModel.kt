package com.example.gitproject.models.dataModel

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class TrendingListModel(
    @SerializedName("avatar") val avatar: String,
    @SerializedName("name") val name: String,
    @SerializedName("repo") val repo: Repo,
    @SerializedName("type") val type: String,
    @SerializedName("url") val url: String,
    @SerializedName("username") val username: String
)

data class Repo(
    @SerializedName("description") val description: String,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)


data class BitmapModel(
    val bitmap: Bitmap?
)