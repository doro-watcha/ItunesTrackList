package com.goddoro.watchaassignment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.watchaassignment.navigation.MainMenu

class MainViewModel : ViewModel() {

    val menu : MutableLiveData<MainMenu> = MutableLiveData(MainMenu.SEARCH)
}