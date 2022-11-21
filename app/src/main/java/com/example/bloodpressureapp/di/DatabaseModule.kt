package com.example.bloodpressureapp.di

import android.content.Context
import androidx.room.Room


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
//    @Provides
//    @Singleton
//    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
//        return Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()
//    }
//
//    @Provides
//    fun provideHistoryDao(appDatabase: AppDatabase): HistoryDao {
//        return appDatabase.historyDao
//    }

}