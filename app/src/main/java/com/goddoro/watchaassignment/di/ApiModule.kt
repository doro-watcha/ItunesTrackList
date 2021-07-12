package com.goddoro.watchaassignment.di

import com.goddoro.watchaassignment.data.api.iTunesAPI
import org.koin.dsl.module
import retrofit2.Retrofit


/**
 * created By DORO 2021/07/09
 */

val apiModule = module {

    single { get<Retrofit>().create(iTunesAPI::class.java) }

}