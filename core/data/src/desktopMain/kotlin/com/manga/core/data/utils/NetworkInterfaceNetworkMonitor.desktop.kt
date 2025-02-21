package com.manga.core.data.utils

import com.manga.core.common.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import org.koin.core.annotation.Qualifier
import org.koin.core.annotation.Single
import java.net.NetworkInterface
import kotlin.coroutines.coroutineContext

@Single
internal class NetworkInterfaceNetworkMonitor(
    @Dispatcher.IO
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NetworkMonitor {
    override val isOnline: Flow<Boolean> = flow {
        while (coroutineContext.isActive) {
            emit(isInternetAvailable())
            delay(1000)
        }
    }
        .flowOn(ioDispatcher)
        .distinctUntilChanged()
        .conflate()

    private fun isInternetAvailable(): Boolean {
        return try {
            NetworkInterface.networkInterfaces()
                .toList()
                .any { networkInterface -> networkInterface.isUp && !networkInterface.isLoopback }
        } catch (e: Exception) {
            false
        }
    }
}