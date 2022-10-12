package com.example.news.ui.fragment.favorites.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.news.data.db.entities.News
import com.example.news.databinding.ItemNewsBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class FavoriteNewsAdapter: ListAdapter<News,FavoriteNewsAdapter.ViewHolderFavoriteNews>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFavoriteNews {
        val view = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderFavoriteNews(view)
    }

    override fun onBindViewHolder(holder: ViewHolderFavoriteNews, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolderFavoriteNews(private val viewBinding: ItemNewsBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(news: News) {
            viewBinding.title.text = news.title
            viewBinding.description.text = news.description
            viewBinding.like.visibility = View.GONE

            Picasso
                .get()
                .load(news.urlToImage)
                .into(viewBinding.photo, object : Callback {
                    override fun onSuccess() {
                        viewBinding.progress.visibility = View.GONE
                        viewBinding.photo.visibility = View.VISIBLE
                    }
                    override fun onError(e: Exception?) {
                        viewBinding.progress.visibility = View.GONE
                        viewBinding.photo.visibility = View.GONE
                        viewBinding.error.visibility = View.VISIBLE
                    }
                })
        }

        /*fun setFavorite(news: News) = with(viewBinding.like) {
            setOnClickListener { listener.onClickFavorite(news, viewBinding) }
        }*/

        /*fun setListener(asistencia: Asistencias, position: Int) = with(viewBinding.btnAsistencia) {
            setOnClickListener {
                val now = System.currentTimeMillis()
                when (now - mLastClickTime < CLICK_TIME_INTERVAL) {
                    true -> { return@setOnClickListener }
                    else -> { mLastClickTime = now; listener.onClickAusente(asistencia, position) }
                }
            }
        }*/

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

