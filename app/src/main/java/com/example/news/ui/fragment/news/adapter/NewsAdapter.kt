package com.example.news.ui.fragment.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.data.db.entities.News
import com.example.news.databinding.ItemNewsBinding
import com.example.news.utils.OnClick
import com.example.news.utils.likeAnimation
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class NewsAdapter(private val listener: OnClick): ListAdapter<News, NewsAdapter.ViewHolderNews>(DiffUtilCallback) {

   /* private var newsList: List<News> = listOf()*/

    private var mLastClickTime = System.currentTimeMillis()
    private val CLICK_TIME_INTERVAL: Long = 300

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderNews {
        val view = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderNews(view)
    }

    override fun onBindViewHolder(holder: ViewHolderNews, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.setFavorite(item)
        holder.setListener(item)
    }

    inner class ViewHolderNews(private val viewBinding: ItemNewsBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(news: News) {
            viewBinding.title.text = news.title
            viewBinding.description.text = news.description

            if (news.favorite) {
                likeAnimation(viewBinding.like, R.raw.like, !true)
            } else {
                likeAnimation(viewBinding.like, R.raw.like, !false)
            }

            Picasso
                .get()
                .load(news.urlToImage)
                .into(viewBinding.photo, object : Callback {
                    override fun onSuccess() {
                        viewBinding.progress.visibility = View.GONE
                        viewBinding.error.visibility = View.GONE
                        viewBinding.photo.visibility = View.VISIBLE
                    }
                    override fun onError(e: Exception?) {
                        viewBinding.progress.visibility = View.GONE
                        viewBinding.photo.visibility = View.GONE
                        viewBinding.error.visibility = View.VISIBLE
                    }
                })
        }

        fun setFavorite(news: News) = with(viewBinding.like) {
            setOnClickListener { listener.onClickFavorite(news, viewBinding) }
        }

        /*fun isFavorite(news: News) {
            if (news.favorite) {
                viewBinding.like.setImageResource(R.drawable.twitter_like_full)
            } else {
                viewBinding.like.setImageResource(R.drawable.twitter_like)
            }
            *//*if (news.favorite) {
                likeAnimation(viewBinding.like, R.raw.like, true)
            } else {
                likeAnimation(viewBinding.like, R.raw.like, false)
            }*//*
        }*/

        fun setListener(news: News) = with(viewBinding.root) {
            setOnClickListener {
                val now = System.currentTimeMillis()
                when (now - mLastClickTime < CLICK_TIME_INTERVAL) {
                    true -> { return@setOnClickListener }
                    else -> { mLastClickTime = now; listener.onClick (news) }
                }
            }
        }

    }

    private object DiffUtilCallback : DiffUtil.ItemCallback<News>() {

        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }

    }

}

