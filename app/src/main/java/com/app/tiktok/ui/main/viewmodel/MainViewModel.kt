package com.app.tiktok.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tiktok.model.ResultData
import com.app.tiktok.model.StoriesDataModel
import com.app.tiktok.repository.DataRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val dataRepository: DataRepository): ViewModel() {
    private val _videoListLiveData = MutableLiveData<ResultData<ArrayList<StoriesDataModel>?>>()
    val videoListLiveData: LiveData<ResultData<ArrayList<StoriesDataModel>?>> get() = _videoListLiveData

    init {
        getDataList()
    }

    fun getDataList() = viewModelScope.launch {
        dataRepository.getStoriesData()
            .collect { result ->
                _videoListLiveData.postValue(result)
            }
    }
}