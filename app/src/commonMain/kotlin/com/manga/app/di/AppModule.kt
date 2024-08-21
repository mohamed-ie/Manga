package com.manga.app.di

import com.manga.core.data.di.CoreDataModule
import com.manga.feature.home.di.HomeModule
import org.koin.core.annotation.Module

@Module([HomeModule::class, CoreDataModule::class])
internal class AppModule