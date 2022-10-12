package com.example.news.data.repository

import androidx.lifecycle.LiveData
import com.example.news.data.db.dao.NewsDAO
import com.example.news.data.db.entities.News
import com.example.news.data.services.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dao: NewsDAO
) : ApiService, NewsDAO {

    /** ROOM */
    override fun insertNews(news: News) = dao.insertNews(news)
    override fun news(): LiveData<List<News>> = dao.news()
    override fun newsFavorite(): LiveData<List<News>> = dao.newsFavorite()
    override fun favorite(id: Int, favorite: Boolean) = dao.favorite(id, favorite)
    override fun delete(id: Int) = dao.delete(id)

    /** RETROFIT */
    override fun topPost(apiKey: String) = api.topPost(apiKey)


}