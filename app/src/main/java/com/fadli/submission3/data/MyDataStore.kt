package com.fadli.submission3.data

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.fadli.submission3.util.Constanta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyDataStore(private val context: Context) {
    private val Context.themePreference: DataStore<Preferences> by preferencesDataStore("change theme")

    fun getChangeTheme(): Flow<Boolean> =
        context.themePreference.data.map {
            it[Constanta.THEME_KEY] ?: false
        }

    suspend fun saveChangeTheme(isDarkModeActive: Boolean) {
        context.themePreference.edit {
            it[Constanta.THEME_KEY] = isDarkModeActive
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: MyDataStore? = null

        fun getInstance(context: Context): MyDataStore =
            INSTANCE?: synchronized(this) {
                val newInstance = INSTANCE?: MyDataStore(context)
                    .also { INSTANCE = it }
                newInstance
            }
    }
}