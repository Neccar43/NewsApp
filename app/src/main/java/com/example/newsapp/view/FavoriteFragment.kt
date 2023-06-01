package com.example.newsapp.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.FavoriteAdapter
import com.example.newsapp.database.ArticlesDatabase
import com.example.newsapp.database.UserWithArticles
import com.example.newsapp.databinding.FragmentFavoriteBinding
import com.example.newsapp.model.Article
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
private lateinit var binding:FragmentFavoriteBinding
class FavoriteFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFavoriteBinding.inflate(inflater,container,false)

        CoroutineScope(Dispatchers.Main).launch {
           val articleList= getAllFavoriteNews(requireContext())
            binding.favoriteRecyclerView.apply {
               adapter=FavoriteAdapter(articleList)
               layoutManager=LinearLayoutManager(activity)
           }
        }




        return binding.root
    }
    /*private suspend fun getAllFavoriteNews(context: Context): UserWithArticles {
        val uid= Firebase.auth.currentUser?.uid?:""
        val dao = ArticlesDatabase(context).articlesDao()
        return dao.getUserWithArticles(uid)
    }*/

    private suspend fun getAllFavoriteNews(context: Context): List<Article> {
        val uid = Firebase.auth.currentUser?.uid ?: ""
        val dao = ArticlesDatabase(context).articlesDao()
        return dao.getAllFavoriteNews(uid)
    }

}