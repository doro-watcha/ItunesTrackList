package com.goddoro.watchaassignment.data

import android.os.Parcelable
import androidx.databinding.ObservableBoolean
import com.goddoro.watchaassignment.util.MusicItemDeserializer
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@JsonAdapter(MusicItemDeserializer::class)
@Parcelize
data class MusicItem(
    @SerializedName("trackId")
    val trackId: Int,

    @SerializedName("trackName")
    val trackName: String,

    @SerializedName("artistName")
    val artistName: String,

    @SerializedName("collectionName")
    val collectionName: String,

    @SerializedName("artworkUrl60")
    val artworkUrl60: String? = "",

    @SerializedName("artworkUrl100")
    val artworkUrl100: String? = "",

    var isFavorite : ObservableBoolean = ObservableBoolean(false)

) : Parcelable