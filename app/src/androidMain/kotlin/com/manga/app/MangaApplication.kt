package com.manga.app

import android.app.Application
import com.manga.app.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class MangaApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(applicationContext)
            modules(AppModule().module)
        }
    }
}