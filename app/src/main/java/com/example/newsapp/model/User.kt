package com.example.newsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false)
    val userId: String ,
    val name: String,
    val lastName: String,
    val email: String,
    val password: String,
    val isActive:Boolean=true
)


