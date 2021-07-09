package com.goddoro.watchaassignment.di

import com.goddoro.watchaassignment.data.repository.ITunesRepository
import com.goddoro.watchaassignment.data.repositoryImpl.ITunesRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {

    single { ITunesRepositoryImpl(get()) } bind ITunesRepository::class
}