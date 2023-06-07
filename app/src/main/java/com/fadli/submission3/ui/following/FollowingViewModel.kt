package com.fadli.submission3.ui.following

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fadli.submission3.room.Repository

class FollowingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(application)

    fun getFollowing(username: String) = repository.getFollowing(username)
}