package com.kgk.task1.base

import android.app.Application
import android.content.SharedPreferences
import com.kgk.task1.ui.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val viewModelModule = module {
    factory { HomeViewModel(get()) }
}

val prefModule = module {
    single {
        getSharedPrefs(androidApplication())
    }
    single<SharedPreferences.Editor> {
        getSharedPrefs(androidApplication()).edit()
    }
}

fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences("default", android.content.Context.MODE_PRIVATE)
}