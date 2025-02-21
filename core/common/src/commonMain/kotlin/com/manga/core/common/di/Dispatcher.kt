package com.manga.core.common.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Qualifier
import org.koin.core.annotation.Single

annotation class Dispatcher {
    @Qualifier
    annotation class IO

    @Qualifier
    annotation class Default
}


@Single
@Dispatcher.Default
internal fun defaultDispatcher() = Dispatchers.Default

@Single
@Dispatcher.IO
internal fun iODispatcher() = Dispatchers.IO