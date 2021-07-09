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
import com.goddoro.watchaassignment.util.toFavoriteItem
import kotlinx.coroutines.launch
import net.bytebuddy.implementation.bytecode.Throw

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
    val onInsertCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val onDeleteCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Throwable> = MutableLiveData()

    /**
     * 불러올 때 favorite list를 검사해서 별표를 체크해둘지 비워둘지 판단
     */

    init {

        listSearchItems()
    }

    private fun listSearchItems() {

        viewModelScope.launch {
            kotlin.runCatching {
                if ( favoriteList.value == null) favoriteList.value = favoriteDao.list()
                iTunesRepository.listMusicItem()
            }.onSuccess {
                searchMusicList.value = it

                searchMusicList.value?.forEach {
                    it.isFavorite = ObservableBoolean(false)
                }

                favoriteList.value?.forEach { favoriteItem ->
                    val musicItem = searchMusicList.value?.find { musicItem ->
                        favoriteItem.collectionId == musicItem.collectionId
                    }
                    musicItem?.isFavorite = ObservableBoolean(true)
                }



                onLoadCompleted.value = true
            }.onFailure {
                errorInvoked.value = it
                onLoadCompleted.value = true
            }
        }
    }

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

    fun addFavorite( item : MusicItem) {
        viewModelScope.launch{

            kotlin.runCatching {
                favoriteDao.insert( item.toFavoriteItem() )
            }.onSuccess {
                onInsertCompleted.value = Once(Unit)
            }.onFailure {
                errorInvoked.value = it
            }
        }

    }

    fun deleteFavorite ( item : MusicItem) {

        val favoriteItem = favoriteList.value?.find { item.collectionId == it.collectionId}

        Log.d(TAG, favoriteItem.toString())

        viewModelScope.launch {

            kotlin.runCatching {
                favoriteDao.delete(favoriteItem)
            }.onSuccess {
                onDeleteCompleted.value = Once(Unit)
            }.onFailure {
                errorInvoked.value = it
            }
        }
    }

    fun refreshSearch() {
        listSearchItems()
    }


    fun refreshFavorite() {
        listFavoriteItems()
    }
}