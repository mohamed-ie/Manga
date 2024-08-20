package com.manga.core.data.di

import com.manga.core.common.di.CoreCommonModule
import com.manga.core.network.di.CoreNetworkModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [CoreNetworkModule::class,CoreCommonModule::class])
@ComponentScan("com.manga.core.data")
class CoreDataModule