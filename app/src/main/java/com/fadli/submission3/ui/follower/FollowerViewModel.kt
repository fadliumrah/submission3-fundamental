package com.fadli.submission3.ui.follower

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fadli.submission3.room.Repository

class FollowerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(application)

    fun getFollowers(username: String) = repository.getFollowers(username)
}