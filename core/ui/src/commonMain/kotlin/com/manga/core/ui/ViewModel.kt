package com.manga.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

context(ViewModel)
fun <T> MutableSharedFlow<T>.launchEmit(
    value: T,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
) = viewModelScope.launch(
    context = context,
    start = start
) { emit(value) }


context(ViewModel)
inline fun <T> MutableSharedFlow<T>.launchEmit(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    crossinline block: () -> T
) = viewModelScope.launch(
    context = context,
    start = start
) { emit(block())}
