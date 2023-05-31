package com.example.newsapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.FeedAdapter
import com.example.newsapp.databinding.FragmentFavoriteBinding
import com.example.newsapp.databinding.FragmentFeedBinding
import com.example.newsapp.databinding.FragmentSignUpBinding
import com.example.newsapp.model.News
import com.example.newsapp.service.NewsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private lateinit var binding: FragmentFeedBinding
private lateinit var adapter:FeedAdapter
class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
         adapter=FeedAdapter()
        binding.feedRecyclerView.adapter=adapter
        binding.feedRecyclerView.layoutManager=LinearLayoutManager(activity)

        getNewsByCountry("us",1,"d8279f651316488c80b06a6d442cd47d")






        return binding.root
    }

    private fun getNewsByCountry(country:String,pageNumber:Int,apiKey:String){
        val retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NewsAPI::class.java)

        val call = retrofit.getNews(country,pageNumber,apiKey)

        call.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful) {
                    response.body()?.let { news ->
                        adapter.differ.submitList(news.articles)

                    }
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                println("Bir sorunla karşılaştık: ${t.localizedMessage}")
            }

        })
    }



}