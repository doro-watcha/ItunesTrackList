package com.goddoro.watchaassignment.di

import com.goddoro.watchaassignment.util.AppPreference
import com.goddoro.watchaassignment.util.ToastUtil
import org.koin.dsl.module

val utilModule = module {


    single { AppPreference(get())}
    single { ToastUtil(get()) }
}