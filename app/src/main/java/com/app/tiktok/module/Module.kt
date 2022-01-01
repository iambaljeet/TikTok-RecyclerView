package com.app.tiktok.module

import com.app.tiktok.mock.Mock
import com.app.tiktok.repository.DataRepository
import com.app.tiktok.ui.main.viewmodel.MainViewModel
import com.app.tiktok.utils.MediaManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modulesList = module {
    single {
        MediaManager(androidContext())
    }
    single {
        Mock(androidContext())
    }
    single {
        DataRepository(get())
    }
    viewModel {
        MainViewModel(get())
    }
}