package com.fadli.submission3.api

import com.fadli.submission3.data.SearchResponse
import com.fadli.submission3.data.DataUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun searchUsers (@Query("q") query: String): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUser (@Path("username") username: String): Call<DataUser>

    @GET("users/{username}/{type}")
    fun getFollow (
        @Path("username") username: String,
        @Path("type") type: String
    ): Call<List<DataUser>>

}