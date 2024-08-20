package com.manga.core.common.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.core.annotation.Qualifier
import org.koin.core.annotation.Single

@Qualifier
annotation class MangaScope

@Single
internal fun mangaScope(@Dispatcher(MangaDispatchers.Default) dispatcher: CoroutineDispatcher) =
    CoroutineScope(SupervisorJob() + dispatcher)