package com.itera.profileapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Inisialisasi DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreferencesRepository(private val context: Context) {

    // Mendefinisikan Key untuk data yang disimpan
    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    private val SORT_ORDER_KEY = booleanPreferencesKey("sort_order_asc") 

    // Membaca status Dark Mode (Default: false)
    val isDarkMode: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_MODE_KEY] ?: false 
        }

    // Membaca status Urutan (Default: false / Terbaru di atas)
    val isSortOrderAsc: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[SORT_ORDER_KEY] ?: false
        }

    // Fungsi untuk menyimpan status Dark Mode
    suspend fun toggleDarkMode(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isDark
        }
    }

    // Fungsi untuk menyimpan status Urutan
    suspend fun toggleSortOrder(isAsc: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SORT_ORDER_KEY] = isAsc
        }
    }
}