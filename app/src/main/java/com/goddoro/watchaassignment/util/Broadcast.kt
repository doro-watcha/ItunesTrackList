package com.goddoro.watchaassignment.util

import io.reactivex.subjects.PublishSubject

object Broadcast {

    val searchListReselectBroadcast : PublishSubject<Unit> = PublishSubject.create()
    val favoriteListReselectBroadcast : PublishSubject<Unit> = PublishSubject.create()
}