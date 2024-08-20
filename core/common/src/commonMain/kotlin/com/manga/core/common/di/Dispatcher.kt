package com.manga.core.common.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.annotation.Qualifier
import org.koin.core.annotation.Single

@Qualifier
annotation class Dispatcher(val dispatcher: MangaDispatchers)

enum class MangaDispatchers { IO, Default }

@Single
@Dispatcher(MangaDispatchers.Default)
internal fun mangaDefaultDispatcher() = Dispatchers.Default

@Single
@Dispatcher(MangaDispatchers.IO)
internal fun mangaIODispatcher() = Dispatchers.IO