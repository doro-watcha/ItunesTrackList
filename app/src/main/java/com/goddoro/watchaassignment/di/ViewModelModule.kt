package com.goddoro.watchaassignment.di

import com.goddoro.watchaassignment.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{


    viewModel { MainViewModel(get(),get()) }
}