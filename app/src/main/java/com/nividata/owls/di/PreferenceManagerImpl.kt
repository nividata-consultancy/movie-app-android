package com.nividata.owls.di

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.nividata.owls.domain.core.preference.PreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.uiModePrefDataStore by preferencesDataStore("ui_mode_pref")

class PreferenceManagerImpl(context: Context) : PreferenceManager {

    private val dataStore = context.uiModePrefDataStore

    override val uiModeFlow: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preference -> preference[IS_DARK_MODE] ?: false }

    override suspend fun setDarkMode(enable: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = enable
        }
    }

    companion object {
        val IS_DARK_MODE = booleanPreferencesKey("dark_mode")
    }
}
