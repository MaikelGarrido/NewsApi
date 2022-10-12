package com.example.news.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.news.data.db.entities.News
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: News)

    @Query("SELECT * FROM news ORDER BY _id")
    fun news() : LiveData<List<News>>

    @Query("SELECT * FROM news WHERE favorite = 1 ORDER BY _id")
    fun newsFavorite() : LiveData<List<News>>

    @Query("UPDATE news set favorite =:favorite WHERE _id = :id")
    fun favorite(id: Int, favorite: Boolean)

    @Query("DELETE FROM news WHERE _id = :id")
    fun delete(id: Int)


}