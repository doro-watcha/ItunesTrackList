package com.goddoro.watchaassignment.di

import com.goddoro.watchaassignment.util.AppPreference
import org.koin.dsl.module

val utilModule = module {


    single { AppPreference(get())}
}