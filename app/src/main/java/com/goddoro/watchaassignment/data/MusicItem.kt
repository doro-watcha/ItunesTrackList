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
    val trackId: Int,

    val trackName: String,

    val artistName: String,

    val collectionName: String,

    val artworkUrl60: String? = "",

    val artworkUrl100: String? = "",

    var isFavorite : ObservableBoolean = ObservableBoolean(false)

) : Parcelable