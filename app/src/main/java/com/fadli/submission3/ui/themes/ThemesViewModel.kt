package com.fadli.submission3.ui.themes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fadli.submission3.room.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ThemesViewModel(application: Application): AndroidViewModel(application) {
    private val repository = Repository(application)

    fun getChangeTheme() = repository.getChangeTheme().asLiveData(Dispatchers.IO)

    fun saveChangeTheme(isDarkModeActive: Boolean) = viewModelScope.launch {
        repository.saveChangeTheme(isDarkModeActive)
    }
}