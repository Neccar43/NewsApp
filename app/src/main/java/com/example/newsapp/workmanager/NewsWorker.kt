package com.example.newsapp.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.newsapp.model.News
import com.example.newsapp.service.NewsAPI
import com.example.newsapp.util.NotificationHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            getNewsBackground("us", 1, "d8279f651316488c80b06a6d442cd47d")
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun getNewsBackground(country: String, pageNumber: Int, apiKey: String) {
        val retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NewsAPI::class.java)

        val call = retrofit.getNews(country, pageNumber, apiKey)

        call.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful) {
                    response.body()?.let { news ->
                        val notificationHelper = NotificationHelper(context)
                            .showNotification(
                                news.articles.first().title,
                                news.articles.first().description
                            )

                    }
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                println("Bir sorunla karşılaştık: ${t.localizedMessage}")
            }

        })
    }

}
