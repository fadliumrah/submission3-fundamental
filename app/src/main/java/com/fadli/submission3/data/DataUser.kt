package com.fadli.submission3.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "dataUser")
data class DataUser(

    @field:Json(name = "id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int?,

    @field:Json(name = "avatar_url")
    @ColumnInfo(name = "avatar")
    val avatar: String?,

    @field:Json(name = "login")
    @ColumnInfo(name = "username")
    val username: String?,

    @field:Json(name = "name")
    @ColumnInfo(name = "name")
    val name: String?,

    @field:Json(name = "company")
    @ColumnInfo(name = "company")
    val company: String?,

    @field:Json(name = "location")
    @ColumnInfo(name = "location")
    val location: String?,

    @field:Json(name = "public_repos")
    @ColumnInfo(name = "repository")
    val repository: Int?,

    @field:Json(name = "followers")
    @ColumnInfo(name = "follower")
    val follower: Int?,

    @field:Json(name = "following")
    @ColumnInfo(name = "following")
    val following: Int?,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean?
)
