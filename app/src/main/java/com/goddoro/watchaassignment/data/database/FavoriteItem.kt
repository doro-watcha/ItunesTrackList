package com.goddoro.watchaassignment.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "FavoriteItem")
@Parcelize
data class FavoriteItem(
    /**
     * PK
     */
    @PrimaryKey(autoGenerate = false)
    @Expose
    val collectionId: Int = 0,

    val trackName : String,

    val artistName : String,

    val collectionName : String,

    val artworkUrl60 : String,

    val artworkUrl100 : String
) : Parcelable