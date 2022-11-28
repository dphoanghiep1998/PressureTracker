package com.example.bloodpressureapp.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bloodpressureapp.data.database.entity.HistoryEntity
import com.example.bloodpressureapp.ui.main.tracker.model.HistoryModel

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(historyEntity: HistoryEntity)

    @Delete
    fun deleteHistory(historyEntity: HistoryEntity)

    @Query("delete from history")
    fun deleteAllHistory()

    @Query("select * from history order by date asc, time asc ")
    fun getListHistoryAsc(): LiveData<List<HistoryModel>>

    @Query("select * from history order by date desc, time desc")
    fun getListHistoryDesc(): LiveData<List<HistoryModel>>


    @Query("select * from history order by id desc")
    fun getListHistory(): LiveData<List<HistoryModel>>
}