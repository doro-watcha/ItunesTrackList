package com.goddoro.watchaassignment.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicItem(
    @SerializedName("wrapperType")
    val wrapperType: String,

    @SerializedName("artistId")
    val artistId: Int,

    @SerializedName("collectionId")
    val collectionId: Int,

    @SerializedName("artistName")
    val artistName : String,

    @SerializedName("collectionName")
    val collectionName : String,

    @SerializedName("collectionCensoredName")
    val collectionCensoredName : String,

    @SerializedName("artistViewUrl")
    val artistViewUrl : String?= "",

    @SerializedName("collectionViewUrl")
    val collectionViewUrl : String? = "",

    @SerializedName("artworkUrl60")
    val artworkUrl60 : String? = "",

    @SerializedName("artworkUrl100")
    val artworkUrl100: String? = "",

    @SerializedName("collectionPrice")
    val collectionPrice : Float,

    @SerializedName("collectionExplicitness")
    val collectionExplicitness : String,

    @SerializedName("trackExplicitness")
    val trackExplicitness : String,

    @SerializedName("discCount")
    val discCount : Int,

    @SerializedName("discNumber")
    val discNumber : Int,

    @SerializedName("trackCount")
    val trackCount : Int,

    @SerializedName("trackNumber")
    val trackNumber : Int,

    @SerializedName("trackTimeMillis")
    val trackTimeMillis : Int,

    @SerializedName("country")
    val country : String,

    @SerializedName("currency")
    val currency : String,

    @SerializedName("primaryGenreName")
    val primaryGenreName : String,

    @SerializedName("contentAdvisoryRating")
    val contentAdvisoryRating : String,

    @SerializedName("isStreamable")
    val isStreamable : Boolean?= false



    ) : Parcelable