package com.example.newsapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.database.ArticlesDatabase
import com.example.newsapp.databinding.NewsRowBinding
import com.example.newsapp.model.Article
import com.example.newsapp.util.deleteNewsFromRoom
import com.example.newsapp.util.formatTimeAgo
import com.example.newsapp.util.isNewsExistRoom
import com.example.newsapp.util.storeFavoriteNews
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteAdapter(private val articleList: List<Article>) :
    RecyclerView.Adapter<FavoriteAdapter.NewsHolder>() {
    class NewsHolder(val binding: NewsRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val binding = NewsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsHolder(binding)
    }


    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val context = holder.itemView.context
        val article = articleList.get(position)
        holder.binding.apply {
            tvSource.text = article.source.name
            tvTitle.text = article.title
            tvPublishedAt.text = formatTimeAgo(article.publishedAt)

        }
        Picasso.get().load(article.urlToImage).into(holder.binding.ivArticleImage)

        holder.binding.addFavoriteButton.setOnClickListener { view ->
            CoroutineScope(Dispatchers.Main).launch {
                val uid= Firebase.auth.currentUser?.uid?:""
                if (isNewsExistRoom(context, article.url,uid)) {
                    deleteNewsFromRoom(context, article.url,uid)
                    Snackbar.make(view, "Haber favorilerden çıkarıldı.", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    storeFavoriteNews(context, article)
                    Snackbar.make(view, "Haber favorilere eklendi.", Snackbar.LENGTH_SHORT)
                        .show()


                }

            }
        }

    }

    override fun getItemCount(): Int {
        return articleList.size
    }


}