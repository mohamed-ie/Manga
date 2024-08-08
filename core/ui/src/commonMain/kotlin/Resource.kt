package core.ui

import BuildConfig
import core.common.Resource
import core.common.onFailure
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.message_unexpected_error

inline fun <D, E : Throwable> Resource<D, E>.onFailure(block: (UiText) -> Unit) =
    onFailure { expected, unexpected ->
        var message = expected?.message?.let(UiText::plain)
        if (BuildConfig.DEBUG)
            message = unexpected?.message?.let(UiText::plain)

        block(message ?: UiText.resource(Res.string.message_unexpected_error))
    }