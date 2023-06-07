package com.fadli.submission3.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.fadli.submission3.room.Repository
import kotlinx.coroutines.Dispatchers

class SplashScreenViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository(application)

    fun getChangeTheme() = repository.getChangeTheme().asLiveData(Dispatchers.IO)

}