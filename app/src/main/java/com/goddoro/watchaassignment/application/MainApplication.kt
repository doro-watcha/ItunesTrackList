package com.goddoro.watchaassignment.application

import android.app.Application
import android.content.Context
import com.goddoro.watchaassignment.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class MainApplication : Application() {

    companion object {
        private lateinit var mainApp: MainApplication
        val context: Context by lazy {
            mainApp.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        inject()
    }

    private fun inject() {
        startKoin {
            androidContext(this@MainApplication)
            androidLogger(Level.INFO)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    repositoryModule,
                    apiModule,
                    utilModule,
                    favoriteDatabaseModule
                )
            )
        }
    }

}