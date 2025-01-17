package com.manga.core.common.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Named
import org.koin.core.annotation.Qualifier
import org.koin.core.annotation.Single

annotation class Dispatcher {
    @Qualifier
    annotation class IO

    @Qualifier
    annotation class Default
}


@Single
@Qualifier(Dispatcher.Default::class)
internal fun defaultDispatcher() = Dispatchers.Default

@Single
@Qualifier(Dispatcher.IO::class)
internal fun iODispatcher() = Dispatchers.IO