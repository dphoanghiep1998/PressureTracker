package com.example.bloodpressureapp.data.services

import com.example.bloodpressureapp.data.database.dao.HistoryDao
import javax.inject.Inject

class HistoryLocalService @Inject constructor(val historyDao: HistoryDao) {
}