package com.example.bloodpressureapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.share_preference.AppSharePreference
import com.example.bloodpressureapp.common.utils.notifyObserver
import com.example.bloodpressureapp.data.repositories.AppRepo
import com.example.bloodpressureapp.ui.main.tracker.model.HistoryModel
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val appRepo: AppRepo, @ApplicationContext private val context: Context
) : ViewModel() {
    var settingLanguageLocale = ""
    var userActionRate = false


    fun getAllHistoryDesc(): LiveData<List<HistoryModel>> {
        return appRepo.getAllHistoryDesc()
    }
    fun getAllHistoryAcs(): LiveData<List<HistoryModel>> {
        return appRepo.getAllHistoryAsc()
    }
    fun getAllHistory(): LiveData<List<HistoryModel>> {
        return appRepo.getAllHistory()
    }


    fun deleteHistory(historyModel: HistoryModel) {
        viewModelScope.launch {
            appRepo.deleteHistory(historyModel)
        }
    }

    fun deleteAllHistory() {
        viewModelScope.launch {
            appRepo.deleteAllHistory()
        }
    }

    fun insertHistory(historyModel: HistoryModel) {
        viewModelScope.launch {
            appRepo.insertHistoryModel(historyModel)
        }
    }

    init {
        settingLanguageLocale = AppSharePreference.INSTANCE.getSavedLanguage("en")
    }

}