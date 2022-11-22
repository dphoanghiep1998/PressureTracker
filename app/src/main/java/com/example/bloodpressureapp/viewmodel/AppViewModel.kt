package com.example.bloodpressureapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {
    var settingLanguageLocale = ""

    private val notesList = MutableLiveData<MutableList<String>>()
    val liveNoteList: LiveData<MutableList<String>>
        get() = notesList

    fun setLiveNoteList(list: MutableList<String>) {
        notesList.value = list
    }

    init {

    }
}