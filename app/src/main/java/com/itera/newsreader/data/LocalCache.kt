package com.itera.newsreader.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// Membuat DataStore
val Context.dataStore by preferencesDataStore(name = "news_cache")

class LocalCache(private val context: Context) {
    private val NEWS_KEY = stringPreferencesKey("cached_news")

    // Menyimpan list artikel ke lokal dalam bentuk JSON string
    suspend fun saveNews(articles: List<Article>) {
        val jsonString = Json.encodeToString(articles)
        context.dataStore.edit { preferences ->
            preferences[NEWS_KEY] = jsonString
        }
    }

    // Mengambil artikel dari lokal saat offline
    suspend fun getCachedNews(): List<Article>? {
        val jsonString = context.dataStore.data.map { it[NEWS_KEY] }.first()
        return if (jsonString != null) {
            Json.decodeFromString(jsonString)
        } else {
            null
        }
    }
}