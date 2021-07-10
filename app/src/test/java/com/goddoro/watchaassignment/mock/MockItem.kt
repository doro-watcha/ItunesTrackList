package com.goddoro.watchaassignment.mock

import androidx.databinding.ObservableBoolean
import com.goddoro.watchaassignment.data.MusicItem

object MockItem {

    val realMusicItem = MusicItem(
        trackId = 1295796863,
        trackName = "2000 Light Years Away",
        artistName = "Green Day",
        collectionName = "Greatest Hits: God's Favorite Band",
        artworkUrl60 = "https://is5-ssl.mzstatic.com/image/thumb/Music124/v4/34/87/5c/34875cbb-b972-4ad8-305a-16a6462c206e/source/60x60bb.jpg",
        artworkUrl100 = "https://is5-ssl.mzstatic.com/image/thumb/Music124/v4/34/87/5c/34875cbb-b972-4ad8-305a-16a6462c206e/source/100x100bb.jpg",
        isFavorite = ObservableBoolean(false)
    )
}