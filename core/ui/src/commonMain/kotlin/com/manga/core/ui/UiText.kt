package com.manga.core.ui

import androidx.compose.runtime.Composable
import com.manga.core.ui.UiText.Plain
import com.manga.core.ui.UiText.Resource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

sealed interface UiText {
    companion object;
    data class Resource(val res: StringResource, val args: List<Any> = emptyList()) : UiText
    data class Plain(val value: String) : UiText
}

fun UiText.Companion.resource(res: StringResource, vararg args: Any) = Resource(res, args.toList())

fun UiText.Companion.plain(value: String) = Plain(value)

@Composable
fun UiText.asString() = when (this) {
    is Plain -> this.value
    is Resource -> stringResource(this.res, * args.toTypedArray())
}

suspend fun UiText.getString() = when (this) {
    is Plain -> this.value
    is Resource -> getString(this.res, * args.toTypedArray())
}