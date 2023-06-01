package com.example.newsapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.newsapp.model.Article
import com.example.newsapp.model.User

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteNews(article: Article):Long

   @Query("SELECT * FROM articles WHERE userId=:uid")
    suspend fun getAllFavoriteNews(uid:String):List<Article>

    @Query("SELECT EXISTS(SELECT 1 FROM articles WHERE url = :url AND userId=:uid)")
    suspend fun isNewsExistRoom(url: String,uid:String): Boolean

    @Query("DELETE FROM articles WHERE url=:url AND userId=:uid")
    suspend fun deleteNews(url: String,uid:String)



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user:User)

    @Transaction
    @Query("SELECT * FROM users WHERE userId=:userId")
    suspend fun getUserWithArticles(userId:String):UserWithArticles

    @Query("UPDATE users SET isActive=0 WHERE isActive=1")
    suspend fun deactivateUser()

    @Query("UPDATE users SET isActive=1 WHERE email=:email AND password=:password")
    suspend fun activateUser(email:String,password:String)

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE userId=:userId )")
    suspend fun isUserExistInRoom(userId: String):Boolean

    @Query("DELETE FROM users WHERE userId=:userId")
    suspend fun deleteUserFromRoom(userId: String)

    @Query("DELETE FROM articles WHERE userId=:userId")
    suspend fun deleteAllFavoriteNews(userId: String)

    @Query("UPDATE users SET email=:newEmail WHERE userId=:userId")
    suspend fun updateEmail(newEmail:String,userId: String)

 @Query("UPDATE users SET password=:newPassword WHERE userId=:userId")
 suspend fun updatePassword(newPassword:String,userId: String)

}