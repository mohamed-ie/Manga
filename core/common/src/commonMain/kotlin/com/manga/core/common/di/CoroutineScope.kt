package com.manga.core.common.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.core.annotation.Named
import org.koin.core.annotation.Qualifier
import org.koin.core.annotation.Single

@Named
annotation class ApplicationScope

@Single
@ApplicationScope
internal fun applicationScope(
    @Qualifier(Dispatcher.Default::class) dispatcher: CoroutineDispatcher
) = CoroutineScope(SupervisorJob() + dispatcher)