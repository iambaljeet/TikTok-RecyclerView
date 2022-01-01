package com.app.tiktok.app

import android.app.Application
import com.app.tiktok.module.modulesList
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp: Application() {
    companion object {
        lateinit var simpleCache: SimpleCache
    }

    override fun onCreate() {
        super.onCreate()
        val leastRecentlyUsedCacheEvictor = LeastRecentlyUsedCacheEvictor(90 * 1024 * 1024)
        val databaseProvider: DatabaseProvider = StandaloneDatabaseProvider(this)
        simpleCache = SimpleCache(cacheDir, leastRecentlyUsedCacheEvictor, databaseProvider)

        startKoin {
            androidContext(this@MyApp)
            modules(modulesList)
        }
    }
}