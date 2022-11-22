package com.example.bloodpressureapp.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.bloodpressureapp.data.database.entity.HistoryEntity
import com.example.bloodpressureapp.ui.main.tracker.model.HistoryModel

@Dao
interface HistoryDao {
    @Insert
    fun insertHistory(historyEntity: HistoryEntity)

    @Delete
    fun deleteHistory(historyEntity: HistoryEntity)

    @Query("delete from history")
    fun deleteAllHistory()

    @Query("select * from history order by time desc")
    fun getListHistory(): LiveData<List<HistoryModel>>
}