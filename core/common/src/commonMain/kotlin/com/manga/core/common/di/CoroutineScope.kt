package com.manga.core.common.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.core.annotation.Single

annotation class ApplicationScope

@Single
@ApplicationScope
internal fun applicationScope(
    @Dispatcher.Default dispatcher: CoroutineDispatcher
) = CoroutineScope(SupervisorJob() + dispatcher)