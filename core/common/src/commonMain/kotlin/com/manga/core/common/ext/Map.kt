package com.manga.core.common.ext

@Suppress("UNCHECKED_CAST")
inline fun <reified K, reified V> Map<K, V?>.filterValuesNotNull(): Map<K, V> =
    filterNot { it.value == null } as Map<K, V>