package com.app.tiktok.utils

import android.content.Context
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File

class MediaManager(private val context: Context) {
    private val DOWNLOAD_CONTENT_DIRECTORY = "downloads"

    private var databaseProvider: DatabaseProvider = StandaloneDatabaseProvider(context)
    private val downloadDirectory: File?
        get() {
            var directory = context.getExternalFilesDir(null)
            if (directory == null) {
                directory = context.filesDir
            }
            return directory
        }
    private val downloadContentDirectory = File(downloadDirectory, DOWNLOAD_CONTENT_DIRECTORY)

    private var downloadCache: Cache =
        SimpleCache(downloadContentDirectory, NoOpCacheEvictor(), databaseProvider)

    private var httpDataSourceFactory: HttpDataSource.Factory = DefaultHttpDataSource.Factory()

    private val upstreamFactory = DefaultDataSource.Factory(context, httpDataSourceFactory)

    var cacheDataSourceFactory: CacheDataSource.Factory = CacheDataSource.Factory()
        .setCache(downloadCache)
        .setUpstreamDataSourceFactory(upstreamFactory)
        .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

    var mediaSourceFactory: MediaSourceFactory = DefaultMediaSourceFactory(cacheDataSourceFactory)
}