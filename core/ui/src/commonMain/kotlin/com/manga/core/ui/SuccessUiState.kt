package com.manga.core.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

interface SuccessUiState<Success>

inline fun <reified S, reified T : SuccessUiState<S>> MutableStateFlow<T>.updateSuccess(block: (S) -> T) =
    update { (it as? S)?.let(block) ?: it }

val <reified S,reified T: SuccessUiState<S>> MutableStateFlow<T>.success
    inline get() = this.value as? S

val <reified S,reified T: SuccessUiState<S>> T.success
    inline get() = this as? S