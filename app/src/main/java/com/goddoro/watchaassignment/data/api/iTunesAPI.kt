package com.goddoro.watchaassignment.data.api

import android.os.Parcelable
import com.goddoro.watchaassignment.data.MusicItem
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface iTunesAPI {

    @GET("/search")
    suspend fun listSearchItems(
        @QueryMap parameters: HashMap<String, Any>
    ) : SearchResponse
}

@Parcelize
data class SearchResponse(
    @SerializedName("resultCount")
    val resultCount : Int ,

    @SerializedName("results")
    val results : List<MusicItem>
) : Parcelable