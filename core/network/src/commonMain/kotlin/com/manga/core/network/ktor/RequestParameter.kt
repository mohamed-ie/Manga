package com.manga.core.network.ktor

import com.manga.core.model.request.Request
import com.manga.core.model.request.RequestParameter
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import kotlinx.datetime.Instant

fun HttpRequestBuilder.parametersOf(
    request: Request,
    dateFormat: Instant.() -> String = Instant::toString,
    booleanFormat: Boolean.() -> String = Boolean::toString
) = request.params.map { requestParameter ->
    when (requestParameter) {
        is RequestParameter.ListParameter<*> ->
            requestParameter.value.forEach {
                parameter(
                    key = "${requestParameter.name}[]",
                    value = it
                )
            }

        is RequestParameter.MapParameter<*> ->
            requestParameter.value.forEach { (key, value) ->
                parameter(
                    key = "${requestParameter}[$key]",
                    value = value
                )
            }

        is RequestParameter.DateParameter -> parameter(
            key = requestParameter.name,
            value = requestParameter.value.dateFormat()
        )

        is RequestParameter.BooleanParameter -> parameter(
            key = requestParameter.name,
            value = requestParameter.value.booleanFormat()
        )

        is RequestParameter.StringParameter ->
            parameter(
                key = requestParameter.name,
                value = requestParameter.value
            )
    }
}