package com.example.news.utils

import com.example.news.data.db.entities.News
import com.example.news.databinding.ItemNewsBinding

interface OnClick {
    fun onClick(news: News)
    fun onClickFavorite(favorite: News, viewBinding: ItemNewsBinding)
}