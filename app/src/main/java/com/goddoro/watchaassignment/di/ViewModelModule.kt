package com.goddoro.watchaassignment.di

import com.goddoro.watchaassignment.MainViewModel
import com.goddoro.watchaassignment.presentation.favorite.FavoriteListViewModel
import com.goddoro.watchaassignment.presentation.search.SearchListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{


    viewModel { MainViewModel() }
    viewModel { FavoriteListViewModel() }
    viewModel { SearchListViewModel(get()) }
}