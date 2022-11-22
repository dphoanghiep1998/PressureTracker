package com.example.bloodpressureapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bloodpressureapp.data.converter.Converter
import com.example.bloodpressureapp.data.database.dao.HistoryDao
import com.example.bloodpressureapp.data.database.entity.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val historyDao: HistoryDao
}