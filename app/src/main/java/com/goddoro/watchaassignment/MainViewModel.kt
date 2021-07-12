package com.goddoro.watchaassignment

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.watchaassignment.data.MusicItem
import com.goddoro.watchaassignment.data.database.FavoriteDao
import com.goddoro.watchaassignment.data.database.FavoriteItem
import com.goddoro.watchaassignment.data.repository.ITunesRepository
import com.goddoro.watchaassignment.navigation.MainMenu
import com.goddoro.watchaassignment.util.Once
import com.goddoro.watchaassignment.util.debugE
import com.goddoro.watchaassignment.util.toFavoriteItem
import kotlinx.coroutines.launch
import net.bytebuddy.implementation.bytecode.Throw


/**
 * 같은 ViewModel에서 관리하지 않을 경우 LocalBroadcast로 toggle sync를 맞춰야하는 문제가 생겨 sharedViewModel을 사용하기로 결정
 */
class MainViewModel(
    private val iTunesRepository: ITunesRepository,
    private val favoriteDao : FavoriteDao
) : ViewModel() {

    private val TAG = MainViewModel::class.java.simpleName

    /**
     * Data
     */
    val menu : MutableLiveData<MainMenu> = MutableLiveData(MainMenu.SEARCH)

    val searchMusicList : MutableLiveData<List<MusicItem>> = MutableLiveData()

    val favoriteList : MutableLiveData<List<FavoriteItem>> = MutableLiveData()

    /**
     * Event
     */
    val onLoadCompleted : MutableLiveData<Boolean> = MutableLiveData()
    val onInsertCompleted : MutableLiveData<Once<FavoriteItem>> = MutableLiveData()
    val onDeleteCompleted : MutableLiveData<Once<FavoriteItem>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Throwable> = MutableLiveData()


    init {
        listSearchItems()
    }


    /**
     * For Search Item
     */

    private fun listSearchItems() {

        onLoadCompleted.value = false

        viewModelScope.launch {
            kotlin.runCatching {
                if ( favoriteList.value == null) favoriteList.value = favoriteDao.list()
                iTunesRepository.listMusicItem()
            }.onSuccess {
                searchMusicList.value = it.distinctBy { it.trackId }
                setFavoriteItems()
                onLoadCompleted.value = true
            }.onFailure {
                errorInvoked.value = it
                onLoadCompleted.value = true
            }
        }
    }

    fun refreshSearch() {
        listSearchItems()
    }


    fun needMoreData() {

        onLoadCompleted.value = false

        viewModelScope.launch {

            kotlin.runCatching {

                iTunesRepository.listMusicItem(
                    offset = searchMusicList.value?.size
                )
            }.onSuccess {
                searchMusicList.value = ( ( searchMusicList.value ?: listOf() )+ it ).distinctBy { it.trackId }

                debugE(TAG, searchMusicList.value?.size)
                setFavoriteItems()
                onLoadCompleted.value = true
            }.onFailure {
                errorInvoked.value = it
                onLoadCompleted.value = true
            }

        }
    }

    private fun setFavoriteItems() {
        favoriteList.value?.forEach { favoriteItem ->
            val musicItem = searchMusicList.value?.find { musicItem ->
                favoriteItem.trackId == musicItem.trackId
            }
            musicItem?.isFavorite = ObservableBoolean(true)
        }
    }

    /**
     * For Favorite List
     */

    private fun listFavoriteItems() {

        viewModelScope.launch {
            kotlin.runCatching {
                favoriteDao.list()
            }.onSuccess {
                Log.d(TAG, it.size.toString())
                favoriteList.value = it
            }.onFailure {
                errorInvoked.value = it
            }
        }
    }

    fun addFavorite( item : FavoriteItem) {
        viewModelScope.launch{

            kotlin.runCatching {
                favoriteDao.insert( item )
            }.onSuccess {
                onInsertCompleted.value = Once(item)
            }.onFailure {
                errorInvoked.value = it
            }
        }

    }

    fun deleteFavorite ( item : FavoriteItem) {

        viewModelScope.launch {

            kotlin.runCatching {
                favoriteDao.delete(item)
            }.onSuccess {
                onDeleteCompleted.value = Once(item)
            }.onFailure {
                errorInvoked.value = it
            }
        }
    }


    fun refreshFavorite() {
        listFavoriteItems()
    }
}