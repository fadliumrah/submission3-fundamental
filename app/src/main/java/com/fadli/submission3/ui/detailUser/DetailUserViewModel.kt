package com.fadli.submission3.ui.detailUser

import android.app.Application
import androidx.lifecycle.*
import com.fadli.submission3.data.DataUser
import com.fadli.submission3.room.Repository
import kotlinx.coroutines.launch

class DetailUserViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository(application)


    suspend fun getDetail(username: String) = repository.getDetail(username)

    fun insertFavorite(user: DataUser) = viewModelScope.launch {
        repository.insertFavorite(user)
    }

    fun deleteFavorite(user: DataUser) = viewModelScope.launch {
        repository.deleteFavorite(user)
    }

}