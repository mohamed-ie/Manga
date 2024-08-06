package core.common.ext

@Suppress("UNCHECKED_CAST")
fun <K, V> Map<K, V?>.filterValuesNotNull() = filterNot { it.value == null } as Map<K, V>