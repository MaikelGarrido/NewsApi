package com.example.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.db.entities.News
import com.example.news.data.model.ResponseNews
import com.example.news.data.repository.NewsRepositoryImpl
import com.example.news.utils.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val repo: NewsRepositoryImpl) : ViewModel() {

    /** CONSULTA PARA TRAER NOTICIAS TOP */
    fun topPost(
        onSuccess: (ResponseNews) -> Unit,
        onFailure: (Int, String) -> Unit,
        onConnectionFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = repo.topPost(API_KEY).awaitResponse()
                if (result.isSuccessful && result.body() != null) {
                    val response = result.body()!!
                    onSuccess(response)
                } else {
                    onFailure(result.code(), result.message())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onConnectionFailure(e.message!!)
            }
        }
    }

    /** OBTENER NOTICIAS FAVORITAS DE BASE DE DATOS */
    fun newsFavorite():LiveData<List<News>> = repo.newsFavorite()

    /** INSERTAR NOTICIAS EN BASE DE DATOS */
    fun insert(news: News) = viewModelScope.launch { withContext(IO) { repo.insertNews(news) } }

    /** OBTENER NOTICIAS DE BASE DE DATOS */
    fun news():LiveData<List<News>> = repo.news()

    /** ACTUALIZAR FAVORITO */
    fun favorite(id: Int, favorite: Boolean) = viewModelScope.launch { withContext(IO) { repo.favorite(id, favorite) } }

    /** ELIMINAR NOTICIA */
    fun delete(id: Int) = viewModelScope.launch { withContext(IO) { repo.delete(id) } }





}