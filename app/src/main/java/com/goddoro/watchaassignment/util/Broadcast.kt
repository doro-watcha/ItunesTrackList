package com.goddoro.watchaassignment.util

import com.goddoro.watchaassignment.data.database.FavoriteItem
import io.reactivex.subjects.PublishSubject

object Broadcast {

    val searchListReselectBroadcast : PublishSubject<Unit> = PublishSubject.create()
    val favoriteListReselectBroadcast : PublishSubject<Unit> = PublishSubject.create()
    val onRefreshFavoriteBroadcast : PublishSubject<Unit> = PublishSubject.create()
    val onRefreshFavoriteCompleted : PublishSubject<List<FavoriteItem>> = PublishSubject.create()
}