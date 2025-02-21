package com.manga.core.model.request

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
sealed class RequestParameter<out T>(open val name: String, open val value: T) {
    data class StringParameter(
        override val name: String,
        override val value: String
    ) : RequestParameter<String>(name, value)

    data class BooleanParameter(
        override val name: String,
        override val value: Boolean
    ) : RequestParameter<Boolean>(name, value)

    data class DateParameter(
        override val name: String,
        override val value: Instant
    ) : RequestParameter<Instant>(name, value)

    data class ListParameter<out T>(
        override val name: String,
        override val value: List<T>
    ) : RequestParameter<List<T>>(name, value)

    data class MapParameter<out V>(
        override val name: String,
        override val value: Map<String, V>
    ) : RequestParameter<Map<String, V>>(name, value)
}

fun stringParam(name: String, value: String) = RequestParameter.StringParameter(name, value)
fun anyParam(name: String, value: Any) = RequestParameter.StringParameter(name, value.toString())
fun booleanParam(name: String, value: Boolean) = RequestParameter.BooleanParameter(name, value)
fun dateParam(name: String, value: Instant) = RequestParameter.DateParameter(name, value)
fun <T> listParam(name: String, value: List<T>) = RequestParameter.ListParameter(name, value)
fun <V> mapParam(name: String, vararg value: Pair<String, V>) = RequestParameter.MapParameter(name, value.toMap())