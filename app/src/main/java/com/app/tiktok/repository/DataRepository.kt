package com.app.tiktok.repository

import com.app.tiktok.mock.Mock
import com.app.tiktok.model.ResultData
import com.app.tiktok.model.StoriesDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DataRepository(private val mock: Mock) {
    fun getStoriesData(): Flow<ResultData<ArrayList<StoriesDataModel>?>> {
        return flow {
            emit(ResultData.Loading())

            val dataList = mock.loadMockData()
            if (!dataList.isNullOrEmpty()) {
                emit(ResultData.Success(dataList))
                return@flow
            }
            emit(ResultData.Failed())
        }.flowOn(Dispatchers.IO)
    }
}