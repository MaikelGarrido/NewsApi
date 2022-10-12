package com.example.news.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.news.data.db.dao.NewsDAO
import com.example.news.data.db.entities.News
import com.example.news.data.db.persistence.AppTypeConverters

@Database(entities = [News::class], version = 1, exportSchema = false)
@TypeConverters(AppTypeConverters::class)
abstract class AppDB : RoomDatabase() {

    /** DAO'S */
    abstract fun NewsDAO(): NewsDAO

}