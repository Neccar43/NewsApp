package com.example.newsapp.database

import androidx.room.Embedded
import androidx.room.Relation
import com.example.newsapp.model.Article
import com.example.newsapp.model.User
//1 to N relation
data class UserWithArticles(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",//users tablosundaki kolon
        entityColumn = "userId"//articles tablosundaki kolon
    )
    val articles: List<Article>
)
