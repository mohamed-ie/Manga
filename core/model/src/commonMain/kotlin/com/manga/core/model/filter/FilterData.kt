package com.manga.core.model.filter

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface FilterData<out T> {
    val name: String
    val value: T?

    @Serializable
    @SerialName("range")
    data class Range<out T>(
        override val name: String,
        val min: T,
        val max: T,
        override val value: T? = null
    ) : FilterData<T>

    @Serializable
    @SerialName("range")
    data class Order(
        override val name: String,
        val options: List<String>,
        val descending: Boolean,
        override val value: String? = null,
    ) : FilterData<String>

    @Serializable
    @SerialName("range")
    data class Date(
        override val name: String,
        val pick: Pick,
        override val value: Instant? = null
    ) : FilterData<Instant> {
        enum class Pick { DATE_TIME, DATE, TIME, YEAR, MONTH, DAY }
    }

    @Serializable
    @SerialName("fill")
    data class Fill(
        override val name: String,
        override val value: String? = null
    ) : FilterData<String>

    @Serializable
    @SerialName("multi_fill")
    data class MultiFill(
        override val name: String,
        override val value: ImmutableSet<String>? = null
    ) : FilterData<ImmutableSet<String>>

    @Serializable
    @SerialName("single_choice")
    data class SingleChoice(
        override val name: String,
        val options: ImmutableMap<String, String>,
        override val value: String? = null
    ) : FilterData<String>

    @Serializable
    @SerialName("multi_choice")
    data class MultiChoice(
        override val name: String,
        val options: ImmutableMap<String, String>,
        override val value: ImmutableSet<String>? = null,
    ) : FilterData<ImmutableSet<String>>
}