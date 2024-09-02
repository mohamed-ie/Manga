package com.manga.app.di

import com.manga.core.data.di.CoreDataModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@ComponentScan("com.manga")
@Module([CoreDataModule::class])
internal class AppModule