package com.app.tiktok.work

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.tiktok.utils.Constants
import com.app.tiktok.utils.MediaManager
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.CacheWriter
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val TAG = "PreCachingService"
class PreCachingService(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params), KoinComponent {

    private val mediaManager: MediaManager by inject()

    private var cacheWriter: CacheWriter? = null

    override suspend fun doWork(): Result = coroutineScope {
        val dataList = inputData.getStringArray(Constants.KEY_STORIES_LIST_DATA)

        val jobs = dataList?.map { data ->
            async {
                val dataUri = Uri.parse(data)

                val dataSpec = DataSpec.Builder()
                    .setUri(dataUri)
                    .build()

                val progressListener = CacheWriter.ProgressListener { requestLength: Long, bytesCached: Long, newBytesCached: Long ->
                    val downloadPercentage = (bytesCached * 100.0
                            / requestLength)
                    Log.d(TAG, "downloadPercentage: $downloadPercentage")
                }

                preloadVideo(
                    mediaManager.cacheDataSourceFactory.createDataSource(),
                    dataSpec,
                    progressListener
                )
            }
        }
        jobs?.joinAll()
        Result.success()
    }

    private fun preloadVideo(
        dataSource: CacheDataSource,
        dataSpec: DataSpec,
        progressListener: CacheWriter.ProgressListener?
    ) {
        Log.d(TAG, "preloadVideo")
        try {
            cacheWriter = CacheWriter(
                dataSource,
                dataSpec,
                null,
                progressListener
            )
            cacheWriter?.cache()
        } catch (e: Exception) {
            e.printStackTrace()
            cacheWriter?.cancel()
        }
    }
}