package com.goddoro.watchaassignment.repositoryTest

import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.LargeTest
import com.goddoro.watchaassignment.data.repository.ITunesRepository
import com.goddoro.watchaassignment.di.apiModule
import com.goddoro.watchaassignment.di.networkModule
import com.goddoro.watchaassignment.di.repositoryModule
import com.goddoro.watchaassignment.util.CommonConst.ITEM_OFFSET
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
@LargeTest
class iTunesRepositoryTest : AutoCloseKoinTest() {

    private val TAG = iTunesRepositoryTest::class.java.simpleName

    private lateinit var repository: ITunesRepository


    @Before
    fun setUp() {
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(apiModule)
            modules(networkModule)
            modules(repositoryModule)
        }


        repository = get()
    }

    @Test
    fun `fetch music item`() = runBlocking {


        val result = repository.listMusicItem(offset = ITEM_OFFSET)
        assertEquals(result.size, 200)


    }


}