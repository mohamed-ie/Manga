package com.manga.core.model.request

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


@Serializable
data class Request(val params: Set<RequestParameter<@Contextual Any?>> = emptySet()) {
    @JvmName("reifiedGet")
    inline operator fun <reified T : Any> get(name: String) =
        params.firstOrNull { it.name == name }?.value as T?

    operator fun get(name: String) =
        params.firstOrNull { it.name == name }?.value

    operator fun plus(requestParameter: RequestParameter<*>) =
        copy(params = params + requestParameter)

    fun getList(name: String) =
        params.firstOrNull { it.name == name && it.value is List<Any?> }?.value as? List<Any?>

    fun getMap(name: String) =
        (params.firstOrNull { it.name == name && it is RequestParameter.MapParameter<Any?> } as? RequestParameter.MapParameter<Any?>)?.value
}

fun request(vararg params: RequestParameter<*>) = Request(params.toSet())