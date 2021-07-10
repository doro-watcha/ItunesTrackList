package com.goddoro.watchaassignment.data.repository

import androidx.annotation.IntRange
import com.goddoro.watchaassignment.data.MusicItem
import com.goddoro.watchaassignment.util.CommonConst.ITEM_OFFSET

interface ITunesRepository {

    suspend fun listMusicItem (
        @IntRange(from = 1L, to = 200L) limit: Int?= ITEM_OFFSET,
        term : String = "greenday",
        entity : String = "song",
        offset : Int? = 0
    ) : List<MusicItem>
}