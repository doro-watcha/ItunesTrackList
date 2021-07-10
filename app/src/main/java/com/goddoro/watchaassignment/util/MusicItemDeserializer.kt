package com.goddoro.watchaassignment.util

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.goddoro.watchaassignment.data.MusicItem
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class MusicItemDeserializer : JsonDeserializer<MusicItem> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): MusicItem {
        val jsonObject = json?.asJsonObject ?: throw NullPointerException("Response Json Music Item is null")

        val trackId = jsonObject["trackId"].asInt
        val collectionName = jsonObject["collectionName"].asString
        val trackName = jsonObject["trackName"].asString
        val artistName = jsonObject["artistName"].asString
        val artwork60Url = jsonObject["artworkUrl60"].asString
        val artwork100Url = jsonObject["artworkUrl100"].asString
        val isFavorite = ObservableBoolean(false)

        return MusicItem(trackId,trackName,artistName, collectionName, artwork60Url , artwork100Url, isFavorite)
    }
}