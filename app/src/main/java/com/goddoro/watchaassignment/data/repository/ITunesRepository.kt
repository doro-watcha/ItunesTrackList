package com.goddoro.watchaassignment.data.repository

import androidx.annotation.IntRange
import com.goddoro.watchaassignment.data.MusicItem

interface ITunesRepository {

    fun listMusicItem (
        @IntRange(from = 1L, to = 200L) limit: Int?= 50,
        term : String = "greenday",
        entity : String = "song"
    ) : List<MusicItem>
}