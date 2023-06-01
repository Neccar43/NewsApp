package com.example.newsapp.util

import android.content.Context
import com.example.newsapp.database.ArticlesDatabase
import com.example.newsapp.database.UserWithArticles
import com.example.newsapp.model.Article
import com.example.newsapp.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatTimeAgo(incomingDate: String): String {
    val incomingDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

    val currentDate = Date()

    try {
        val incomingDateTime = incomingDateFormat.parse(incomingDate)

        val diffInMillis = currentDate.time - incomingDateTime.time
        val diffInMinutes = diffInMillis / (60 * 1000)
        val diffInHours = diffInMinutes / 60
        val diffInDays = diffInHours / 24

        val result = when {
            diffInMinutes < 60 && diffInHours == 0L && diffInDays == 0L -> "${diffInMinutes % 60}min ago"
            diffInHours < 24 && diffInMinutes == 0L && diffInDays == 0L -> "${diffInHours % 24}h ago"
            diffInDays > 0 && diffInHours == 0L && diffInMinutes == 0L -> "${diffInDays}d ago"
            diffInHours < 24 && diffInMinutes <60 && diffInDays == 0L  ->"${diffInHours % 24}h ${diffInMinutes % 60}min ago"
            diffInHours ==0L && diffInMinutes <60 && diffInDays >0  ->"${diffInDays}d ${diffInMinutes % 60}min ago"
            diffInHours < 24 && diffInMinutes == 0L && diffInDays >0  ->"${diffInDays}d ${diffInHours % 24}h ago"
            else->"${diffInDays}d ${diffInHours % 24}h ${diffInMinutes % 60}min ago"

        }

        return result
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return ""
}


/*suspend fun storeFavoriteNews(context: Context, article: Article) {
    val dao = ArticlesDatabase(context).articlesDao()
    dao.insertFavoriteNews(article)
}*/
 suspend fun storeFavoriteNews(context: Context, article: Article) {
    val uid = Firebase.auth.currentUser?.uid ?: return // Kullanıcının UID'sini alın, eğer null ise işlemi durdurun
    article.userId = uid // Haberin userId'sini kullanıcının UID'si ile güncelleyin
    val dao = ArticlesDatabase(context).articlesDao()
    dao.insertFavoriteNews(article)
}


suspend fun isNewsExistRoom(context: Context, url: String,uid:String): Boolean {
    val dao = ArticlesDatabase(context).articlesDao()
    return dao.isNewsExistRoom(url,uid)
}

suspend fun deleteNewsFromRoom(context: Context, url: String,uid: String) {
    val dao = ArticlesDatabase(context).articlesDao()
    dao.deleteNews(url,uid)
}

suspend fun storeUserToRoom(context: Context,user:User){
    val dao = ArticlesDatabase(context).articlesDao()
    dao.insertUser(user)
}

suspend fun getFavoriteNews(context: Context,userId:String): UserWithArticles {
    val dao = ArticlesDatabase(context).articlesDao()
    return dao.getUserWithArticles(userId)
}

suspend fun userLogin(context: Context,email:String,password:String){
    val dao = ArticlesDatabase(context).articlesDao()
    dao.activateUser(email,password)
}

suspend fun userLogOut(context: Context){
    val dao = ArticlesDatabase(context).articlesDao()
    dao.deactivateUser()
}

suspend fun isUserExistInRoom(context: Context,userId:String): Boolean {
    val dao = ArticlesDatabase(context).articlesDao()
    return dao.isUserExistInRoom(userId)
}
