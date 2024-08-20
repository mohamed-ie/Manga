package com.manga.core.domain.di

import com.manga.core.data.di.CoreDataModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module([CoreDataModule::class])
@ComponentScan("com.manga.core.domain")
class CoreDomainModule