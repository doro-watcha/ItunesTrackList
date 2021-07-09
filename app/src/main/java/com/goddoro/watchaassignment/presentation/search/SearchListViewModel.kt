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

    val errorInvoked : MutableLiveData<Throwable> = MutableLiveData()

    init {
        listSearchItems()
    }

    fun listSearchItems() {

        viewModelScope.launch {
            kotlin.runCatching {
                iTunesRepository.listMusicItem()

            }.onSuccess {
                searchMusicList.value = it
            }.onFailure {
                errorInvoked.value = it
            }
        }
    }
}