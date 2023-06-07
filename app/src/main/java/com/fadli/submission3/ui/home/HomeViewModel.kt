package com.fadli.submission3.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fadli.submission3.room.Repository

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository(application)

    fun searchUser(query: String) = repository.searchUser(query)
}