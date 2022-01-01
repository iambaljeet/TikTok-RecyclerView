package com.app.tiktok.mock

import android.content.Context
import com.app.tiktok.R
import com.app.tiktok.model.StoriesDataModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Mock(private val context: Context) {
    fun loadMockData(): ArrayList<StoriesDataModel>? {
        val mockData = context.resources.openRawResource(R.raw.stories_data)
        val dataString = mockData.bufferedReader().readText()

        val gson = Gson()
        val storiesType = object : TypeToken<ArrayList<StoriesDataModel>>() {}.type

        return gson.fromJson(dataString, storiesType)
    }
}