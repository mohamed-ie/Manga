package com.manga.core.ui

import com.manga.core.model.manga_dex.common.MangaException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.core_ui_message_unexpected_exception

inline fun CoroutineMangaExceptionHandler(
    crossinline block: (UiText) -> Unit
) = CoroutineExceptionHandler { _, throwable ->
    throwable.printStackTrace()
    var message: UiText? = null
    if (throwable is MangaException) throwable.message?.let { message = UiText.plain(it) }
    block(message ?: UiText.resource(Res.string.core_ui_message_unexpected_exception))
}

inline fun CoroutineScope.launch(
    crossinline mangaExceptionHandler: (UiText) -> Unit,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    noinline block: suspend CoroutineScope.() -> Unit
) = launch(
    context = CoroutineMangaExceptionHandler(mangaExceptionHandler),
    start = start,
    block = block
)