package com.goddoro.watchaassignment.roomTest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.LargeTest
import com.goddoro.watchaassignment.data.database.FavoriteDao
import com.goddoro.watchaassignment.data.database.FavoriteDatabase
import com.goddoro.watchaassignment.di.*
import com.goddoro.watchaassignment.mock.MockItem
import com.goddoro.watchaassignment.repositoryTest.iTunesRepositoryTest
import com.goddoro.watchaassignment.util.toFavoriteItem
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
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
class RoomTest : AutoCloseKoinTest() {

    private val TAG = iTunesRepositoryTest::class.java.simpleName

    private lateinit var dao: FavoriteDao
    private lateinit var dataBase : FavoriteDatabase

    @Before
    fun setup() {

        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(apiModule)
            modules(networkModule)
            modules(viewModelModule)
            modules(repositoryModule)
            modules(favoriteDatabaseModule)
        }



        dataBase = get()
        dao = get()
    }


    @Test
    fun `add music item and delete item`() = runBlocking{

        dao.insert(MockItem.realMusicItem.toFavoriteItem(5000))

        val result = dao.list()

        val first_assert = result.size == 1

        dao.delete(result[0])

        val newResult = dao.list()

        val second_assert = newResult.isEmpty()
        assertEquals(first_assert && second_assert, true)
    }
/*
    @Test
    fun `list favorite item` () = runBlocking {
        val result = dao.list()
        assertEquals(result.size,0)
    }

    @Test
    fun `delete favorite item` () = runBlocking {
        val result = dao.delete(MockItem.realMusicItem.toFavoriteItem(5000))
        assertEquals(true,result)
    }*/


}