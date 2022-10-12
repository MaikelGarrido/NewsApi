package com.example.news.data.di

import android.content.Context
import androidx.room.Room
import com.example.news.data.db.AppDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(context, AppDB::class.java, "News_DB").build()

    @Singleton
    @Provides
    fun provideResultDAO(db: AppDB) = db.NewsDAO()

}