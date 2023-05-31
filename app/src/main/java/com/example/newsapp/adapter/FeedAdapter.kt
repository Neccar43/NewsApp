package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.FragmentFeedBinding
import com.example.newsapp.databinding.NewsRowBinding
import com.example.newsapp.model.Article
import com.squareup.picasso.Picasso

class FeedAdapter(): RecyclerView.Adapter<FeedAdapter.NewsHolder>() {
    class NewsHolder(val binding: NewsRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    private val differCallBack=object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url==newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }

    }

    val differ= AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val binding=NewsRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val article=differ.currentList.get(position)

        holder.binding.apply {
            tvSource.text=article.source.name
            tvDescription.text=article.description
            tvTitle.text=article.title
            tvPublishedAt.text=article.publishedAt

        }
        Picasso.get().load(article.urlToImage).into(holder.binding.ivArticleImage)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}