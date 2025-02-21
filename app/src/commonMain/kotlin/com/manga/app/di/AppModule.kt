package com.manga.app.di

import com.compose.utils.app_event.AppEventInvoker
import com.compose.utils.app_event.DefaultAppEventInvoker
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.manga")
class AppModule

@Single
fun appEventInvoker(): AppEventInvoker = DefaultAppEventInvoker()