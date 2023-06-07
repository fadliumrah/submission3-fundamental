package com.fadli.submission3.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fadli.submission3.data.DataUser

@Database(entities = [DataUser::class], exportSchema = false, version = 1)
abstract class MyDatabase: RoomDatabase() {
    abstract fun dao(): Dao

    companion object{
        @Volatile
        private var INSTANCE: MyDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): MyDatabase{
            if (INSTANCE == null){
                synchronized(MyDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "DataUser.db"
                    ).build()
                }
            }
            return INSTANCE as MyDatabase
        }
    }
}