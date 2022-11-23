package com.example.bloodpressureapp.data.repositories

import androidx.lifecycle.LiveData
import com.example.bloodpressureapp.data.services.HistoryLocalService
import com.example.bloodpressureapp.di.IoDispatcher
import com.example.bloodpressureapp.ui.main.tracker.model.HistoryModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppRepo @Inject constructor(
    private val historyLocalService: HistoryLocalService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun insertHistoryModel(model: HistoryModel) = withContext(dispatcher) {
        historyLocalService.historyDao.insertHistory(model.toHistoryEntity())
    }

    suspend fun deleteAllHistory() =
        withContext(dispatcher) {
            historyLocalService.historyDao.deleteAllHistory()
        }

    suspend fun deleteHistory(model: HistoryModel) = withContext(dispatcher) {
        historyLocalService.historyDao.deleteHistory(model.toHistoryEntity())
    }


    fun getAllHistory(): LiveData<List<HistoryModel>> =
        historyLocalService.historyDao.getListHistory()

}