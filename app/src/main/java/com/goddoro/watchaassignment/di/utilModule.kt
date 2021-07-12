package com.goddoro.watchaassignment.di

import com.goddoro.watchaassignment.util.ToastUtil
import org.koin.dsl.module

val utilModule = module {



    single { ToastUtil(get()) }
}