package com.fadli.submission3.room
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fadli.submission3.data.DataUser

@Dao
interface Dao {

    @Query("SELECT * FROM dataUser ORDER BY username ASC")
    suspend fun getFavoriteListUser(): List<DataUser>

    @Query("SELECT * FROM dataUser WHERE username = :username")
    suspend fun getFavoriteDetailUser(username: String): DataUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(user: DataUser)

    @Delete
    suspend fun deleteFavoriteUser(user: DataUser)

}