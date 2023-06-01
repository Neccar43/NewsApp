package com.example.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.model.Article
import com.example.newsapp.model.User

@Database(entities = [Article::class, User::class], version = 6)
@TypeConverters(Converters::class)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao

    //singleton yapısı
    companion object {
        @Volatile
        private var instance: ArticlesDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticlesDatabase::class.java,
                "articleDatabase"
            )
                .build()


    }

}