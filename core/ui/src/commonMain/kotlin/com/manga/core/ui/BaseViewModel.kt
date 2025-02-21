package com.manga.core.ui

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import com.compose.utils.app_event.AppEventInvoker
import org.koin.core.context.GlobalContext

abstract class BaseViewModel(
    val appEventInvoker: AppEventInvoker = GlobalContext.get().get()
) : ViewModel(), AppEventInvoker by appEventInvoker {

    private var initializeCalled = false

    open fun initialize() {
        if (initializeCalled) return
        initializeCalled = true
    }
}