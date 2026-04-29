package com.itera.profileapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.itera.profileapp.PreferencesRepository
import com.itera.profileapp.SortPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreferencesRepository(private val context: Context) : PreferencesRepository, SortPreferencesRepository {

    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    private val SORT_ORDER_KEY = booleanPreferencesKey("sort_order_asc")

    override val isDarkMode: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[DARK_MODE_KEY] ?: false }

    override val isSortOrderAsc: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[SORT_ORDER_KEY] ?: false }

    override suspend fun toggleDarkMode(isDark: Boolean) {
        context.dataStore.edit { preferences -> preferences[DARK_MODE_KEY] = isDark }
    }

    override suspend fun toggleSortOrder(isAsc: Boolean) {
        context.dataStore.edit { preferences -> preferences[SORT_ORDER_KEY] = isAsc }
    }
}