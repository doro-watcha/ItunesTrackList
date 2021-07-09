package com.goddoro.watchaassignment.data.repositoryImpl

import com.goddoro.watchaassignment.data.MusicItem
import com.goddoro.watchaassignment.data.api.iTunesAPI
import com.goddoro.watchaassignment.data.repository.ITunesRepository
import com.goddoro.watchaassignment.util.filterValueNotNull

class ITunesRepositoryImpl( val api : iTunesAPI) : ITunesRepository {


    override fun listMusicItem(limit: Int?, term: String, entity: String): List<MusicItem> {
        val params = hashMapOf(
            "limit" to limit,
            "term" to term,
            "entity" to entity
        ).filterValueNotNull()
        return api.listSearchItems(params).results
    }
}