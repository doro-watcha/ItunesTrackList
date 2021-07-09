package com.goddoro.watchaassignment.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.watchaassignment.data.MusicItem
import com.goddoro.watchaassignment.data.repository.ITunesRepository
import kotlinx.coroutines.launch

class SearchListViewModel (
    private val iTunesRepository: ITunesRepository
        ): ViewModel() {

    val searchMusicList : MutableLiveData<List<MusicItem>> = MutableLiveData()

    val onLoadCompleted : MutableLiveData<Boolean> = MutableLiveData()
    val errorInvoked : MutableLiveData<Throwable> = MutableLiveData()

    init {
        listSearchItems()
    }

    private fun listSearchItems() {

        viewModelScope.launch {
            kotlin.runCatching {
                iTunesRepository.listMusicItem()

            }.onSuccess {
                searchMusicList.value = it
                onLoadCompleted.value = true
            }.onFailure {
                errorInvoked.value = it
                onLoadCompleted.value = true
            }
        }
    }

    fun refresh() {
        listSearchItems()
    }
}