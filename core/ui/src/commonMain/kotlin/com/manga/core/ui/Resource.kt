package com.manga.core.ui

import com.manga.core.common.Resource
import com.manga.core.common.onFailure
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.message_unexpected_error
import ui.BuildConfig

inline fun <D, E : Throwable> Resource<D, E>.onFailure(block: (UiText) -> Unit) =
    onFailure { expected, unexpected ->
        var message = expected?.message?.let(UiText::plain)
        if (BuildConfig.DEBUG)
            message = unexpected?.message?.let(UiText::plain)

        block(message ?: UiText.resource(Res.string.message_unexpected_error))
    }