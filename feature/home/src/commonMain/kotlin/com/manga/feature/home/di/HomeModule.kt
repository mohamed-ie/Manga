package com.manga.feature.home.di

import com.manga.core.data.di.CoreDataModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module([CoreDataModule::class])
@ComponentScan("com.manga.feature.home")
class HomeModule