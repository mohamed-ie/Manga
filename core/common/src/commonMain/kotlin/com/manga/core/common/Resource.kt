package com.manga.core.common

import com.manga.core.common.Resource.Failure
import com.manga.core.common.Resource.Failure.Expected
import com.manga.core.common.Resource.Failure.Unexpected
import com.manga.core.common.Resource.Success

sealed interface Resource<out D, out E : Throwable> {
    companion object {
        fun <D, E : Throwable> success(data: D): Resource<D, E> = Success(data)
        fun <D, E : Throwable> expectedFailure(cause: E): Resource<D, E> = Expected(cause)
        fun <D, E : Throwable> unexpectedFailure(cause: Throwable): Resource<D, E> = Unexpected(cause)
    }

    data class Success<D>(val data: D) : Resource<D, Nothing>

    sealed interface Failure<out E : Throwable> : Resource<Nothing, E> {
        data class Expected<E : Throwable>(val cause: E) : Failure<E>
        data class Unexpected(val cause: Throwable) : Failure<Nothing>
    }
}

fun <D, E : Throwable> Resource<D, E>.getOrNull() = (this as? Success)?.data

fun <D, E : Throwable> Resource<D, E>.getOrThrow(): D = when (this) {
    is Expected -> throw this.cause
    is Unexpected -> throw this.cause
    is Success -> this.data
}

fun <D, E : Throwable> Resource<D, E>.expectedCauseOrNull() = (this as? Expected<E>)?.cause

fun <D, E : Throwable> Resource<D, E>.unexpectedCauseOrNull() = (this as? Unexpected)?.cause

inline fun <D, E : Throwable> Resource<D, E>.onSuccess(block: (D) -> Unit): Resource<D, E> {
    if (this is Success)
        block(data)
    return this
}

inline fun <D, E : Throwable> Resource<D, E>.onFailure(block: (E?, Throwable?) -> Unit): Resource<D, E> {
    if (this is Failure)
        block((this as? Expected)?.cause, (this as? Unexpected)?.cause)
    return this
}

inline fun <D, E : Throwable> Resource<D, E>.onFailure(block: (Throwable) -> Unit): Resource<D, E> {
    if (this is Failure)
        block((this as? Expected)?.cause ?: (this as Unexpected).cause)
    return this
}

inline fun <D, E : Throwable> Resource<D, E>.onFailure(
    onExpected: (E) -> Unit,
    onUnexpected: (Throwable) -> Unit
): Resource<D, E> {
    when (this) {
        is Expected -> onExpected(cause)
        is Unexpected -> onUnexpected(cause)
        is Success -> Unit
    }

    return this
}

inline fun <D, E : Throwable> Resource<D, E>.onExpectedFailure(block: (E) -> Unit): Resource<D, E> {
    if (this is Expected) block(cause)
    return this
}

inline fun <D, E : Throwable> Resource<D, E>.onUnexpectedFailure(block: (Throwable) -> Unit): Resource<D, E> {
    if (this is Unexpected) block(cause)
    return this
}

inline fun <D, E : Throwable> Resource<D, E>.handle(
    onSuccess: (D) -> Unit,
    onUnexpectedFailure: (Throwable) -> Unit,
    onExpectedFailure: (E) -> Unit,
): Resource<D, E> {
    when (this) {
        is Expected -> onExpectedFailure(cause)
        is Unexpected -> onUnexpectedFailure(cause)
        is Success -> onSuccess(data)
    }
    return this
}

inline fun <I, O, reified E : Throwable> Resource<I, E>.map(transformer: (I) -> O): Resource<O, E> =
    when (this) {
        is Success -> resourceOf<O, E> { transformer(data) }
        is Expected -> this
        is Unexpected -> this
    }

inline fun <D, I : Throwable, O : Throwable> Resource<D, I>.mapExpected(transformer: (I) -> O): Resource<D, O> =
    when (this) {
        is Success -> this
        is Expected -> Resource.expectedFailure(transformer(cause))
        is Unexpected -> this
    }

inline fun <D, reified E : Throwable> resourceOf(
    catch: (e: Throwable) -> Resource<D, E> = { e ->
        if (e is E) Resource.expectedFailure(e)
        else Resource.unexpectedFailure(e)
    },
    block: () -> D
): Resource<D, E> =
    try {
        Resource.success(block())
    } catch (e: Throwable) {
        catch(e)
    }

inline fun <D> resourceOf(block: () -> D): Resource<D, Throwable> =
    try {
        Resource.success(block())
    } catch (e: Throwable) {
        Resource.unexpectedFailure(e)
    }
