package com.goddoro.watchaassignment.di

import androidx.room.Room
import com.goddoro.watchaassignment.data.database.FavoriteDatabase
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val favoriteDatabaseModule = module {
    single(named("FavoriteDatabase")) {
        Room.databaseBuilder(get(), FavoriteDatabase::class.java, "001")
            .allowMainThreadQueries()
            .build()
    } bind FavoriteDatabase::class

    single {
        get<FavoriteDatabase>().favoriteDao()
    }
}