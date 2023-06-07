package com.fadli.submission3.room

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fadli.submission3.api.ApiService
import com.fadli.submission3.api.RetrofitService
import com.fadli.submission3.data.DataUser
import com.fadli.submission3.data.SearchResponse
import com.fadli.submission3.data.MyDataStore
import com.fadli.submission3.util.Resource
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class Repository(application: Application) {
    private val myDataStore: MyDataStore
    private val dao: Dao
    private val retrofit: ApiService

    init {
        myDataStore = MyDataStore.getInstance(application)
        val myDatabase: MyDatabase = MyDatabase.getInstance(application)
        dao = myDatabase.dao()
        retrofit = RetrofitService.create()
    }

    suspend fun saveChangeTheme(isDarkModeActive: Boolean) = myDataStore.saveChangeTheme(isDarkModeActive)

    fun getChangeTheme() = myDataStore.getChangeTheme()

    suspend fun getFavoriteList(): LiveData<Resource<List<DataUser>>>{
        val listFavorite = MutableLiveData<Resource<List<DataUser>>>()
        listFavorite.postValue(Resource.Loading())

        if (dao.getFavoriteListUser().isEmpty()){
            listFavorite.postValue(Resource.Error(null))
        }else{
            listFavorite.postValue((Resource.Success(dao.getFavoriteListUser())))
        }
        return listFavorite
    }


    suspend fun insertFavorite(user: DataUser) = dao.insertFavoriteUser(user)

    suspend fun deleteFavorite(user: DataUser) = dao.deleteFavoriteUser(user)


    fun searchUser(query: String): LiveData<Resource<List<DataUser>>> {
        val listUser = MutableLiveData<Resource<List<DataUser>>>()

        listUser.postValue(Resource.Loading())
        retrofit.searchUsers(query).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val list = response.body()?.items
                if (list.isNullOrEmpty())
                    listUser.postValue(Resource.Error(null))
                else
                    listUser.postValue(Resource.Success(list))
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                listUser.postValue(Resource.Error(t.message))
            }

        })
        return listUser
    }

    suspend fun getDetail(username: String): LiveData<Resource<DataUser>>{
        val user = MutableLiveData<Resource<DataUser>>()

        if (dao.getFavoriteDetailUser(username) != null) {
            user.postValue(Resource.Success(dao. getFavoriteDetailUser(username)))
        } else {
            retrofit.getDetailUser(username).enqueue(object : Callback<DataUser> {
                override fun onResponse(call: Call<DataUser>, response: Response<DataUser>) {
                    val result = response.body()
                    user.postValue(Resource.Success(result))
                }

                override fun onFailure(call: Call<DataUser>, t: Throwable) {
                    user.postValue(Resource.Error(t.message))
                }
            })
        }
        return user
    }


    fun getFollowing(username: String): LiveData<Resource<List<DataUser>>> {

        val listUser = MutableLiveData<Resource<List<DataUser>>>()

        listUser.postValue(Resource.Loading())
        retrofit.getFollow(username, type = "following").enqueue(object : Callback<List<DataUser>> {
            override fun onResponse(call: Call<List<DataUser>>, response: Response<List<DataUser>>) {
                val list = response.body()
                if (list.isNullOrEmpty())
                    listUser.postValue(Resource.Error(null))
                else
                    listUser.postValue(Resource.Success(list))
            }

            override fun onFailure(call: Call<List<DataUser>>, t: Throwable) {
                listUser.postValue(Resource.Error(t.message))
            }
        })
        return listUser
    }

    fun getFollowers(username: String): LiveData<Resource<List<DataUser>>> {

        val listUser = MutableLiveData<Resource<List<DataUser>>>()

        listUser.postValue(Resource.Loading())
        retrofit.getFollow(username, type = "followers").enqueue(object : Callback<List<DataUser>> {
            override fun onResponse(call: Call<List<DataUser>>, response: Response<List<DataUser>>) {
                val list = response.body()
                if (list.isNullOrEmpty())
                    listUser.postValue(Resource.Error(null))
                else
                    listUser.postValue(Resource.Success(list))
            }

            override fun onFailure(call: Call<List<DataUser>>, t: Throwable) {
                listUser.postValue(Resource.Error(t.message))
            }
        })

        return listUser
    }
}