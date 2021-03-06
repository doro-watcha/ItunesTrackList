package com.goddoro.watchaassignment.navigation

import androidx.annotation.IdRes
import com.goddoro.watchaassignment.R

enum class MainMenu(@IdRes override val menuId: Int, override val idx: Int) : IMainMenu {

    SEARCH(R.id.nav_item_search, 0),
    FAVORITE(R.id.nav_item_favorite, 1);

    companion object {
        fun parseIdToMainMenu(@IdRes id: Int) = values().first { it.menuId == id }
    }
}

interface IMainMenu {
    val menuId: Int
    val idx: Int
}