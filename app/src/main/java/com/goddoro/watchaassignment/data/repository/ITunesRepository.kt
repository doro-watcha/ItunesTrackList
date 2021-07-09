package com.goddoro.watchaassignment.data.repository

import androidx.annotation.IntRange
import com.goddoro.watchaassignment.data.MusicItem

interface ITunesRepository {

    suspend fun listMusicItem (
        @IntRange(from = 1L, to = 200L) limit: Int?= 10,
        term : String = "greenday",
        entity : String = "song",
        offset : Int? = 0
    ) : List<MusicItem>
}