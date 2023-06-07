package com.fadli.submission3.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fadli.submission3.room.Repository

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository(application)

    suspend fun getFavoriteList() = repository.getFavoriteList()

}