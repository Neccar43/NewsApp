package com.example.newsapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.model.Article

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteNews(article: Article):Long

    @Query("SELECT * FROM articles")
    suspend fun getAllFavoriteNews():List<Article>

    @Delete
    suspend fun deleteNews(article: Article)

}