package com.manga.core.ui

import androidx.compose.runtime.*
import androidx.paging.*
import com.manga.core.ui.LazyPagingItemsContentState.Empty
import com.manga.core.ui.LazyPagingItemsContentState.Error
import com.manga.core.ui.LazyPagingItemsContentState.Loading
import com.manga.core.ui.LazyPagingItemsContentState.NotLoading
import com.manga.core.ui.LazyPagingItemsContentState.Refreshing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


/**
 * This file coped from [LazyPagingItems](https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:paging/paging-compose/src/commonMain/kotlin/androidx/paging/compose/LazyPagingItems.kt?q=file:androidx%2Fpaging%2Fcompose%2FLazyPagingItems.kt%20class:androidx.paging.compose.LazyPagingItems)
 * 
 * The class responsible for accessing the data from a [Flow] of [PagingData]. In order to obtain an
 * instance of [LazyPagingItems] use the [collectAsLazyPagingItems] extension method of [Flow] with
 * [PagingData]. This instance can be used for Lazy foundations such as [LazyListScope.items] to
 * display data received from the [Flow] of [PagingData].
 *
 * Previewing [LazyPagingItems] is supported on a list of mock data. See sample for how to preview
 * mock data.
 *
 * @sample androidx.paging.compose.samples.PagingPreview
 * @param T the type of value used by [PagingData].
 */
public class LazyPagingItems<T : Any> internal constructor(
    /** the [Flow] object which contains a stream of [PagingData] elements. */
    private val flow: Flow<PagingData<T>>
) {
    private val mainDispatcher = Dispatchers.Main.immediate

    /**
     * If the [flow] is a SharedFlow, it is expected to be the flow returned by from
     * pager.flow.cachedIn(scope) which could contain a cached PagingData. We pass the cached
     * PagingData to the presenter so that if the PagingData contains cached data, the presenter can
     * be initialized with the data prior to collection on pager.
     */
    private val pagingDataPresenter =
        object :
            PagingDataPresenter<T>(
                mainContext = mainDispatcher,
                cachedPagingData =
                if (flow is SharedFlow<PagingData<T>>) flow.replayCache.firstOrNull() else null
            ) {
            override suspend fun presentPagingDataEvent(
                event: PagingDataEvent<T>,
            ) {
                updateItemSnapshotList()
            }
        }

    /**
     * Contains the immutable [ItemSnapshotList] of currently presented items, including any
     * placeholders if they are enabled. Note that similarly to [peek] accessing the items in a list
     * will not trigger any loads. Use [get] to achieve such behavior.
     */
    var itemSnapshotList by mutableStateOf(pagingDataPresenter.snapshot())
        private set

    /** The number of items which can be accessed. */
    val itemCount: Int
        get() = itemSnapshotList.size

    private fun updateItemSnapshotList() {
        itemSnapshotList = pagingDataPresenter.snapshot()
    }

    /**
     * Returns the presented item at the specified position, notifying Paging of the item access to
     * trigger any loads necessary to fulfill prefetchDistance.
     *
     * @see peek
     */
    operator fun get(index: Int): T? {
        pagingDataPresenter[index] // this registers the value load
        return itemSnapshotList[index]
    }

    /**
     * Returns the presented item at the specified position, without notifying Paging of the item
     * access that would normally trigger page loads.
     *
     * @param index Index of the presented item to return, including placeholders.
     * @return The presented item at position [index], `null` if it is a placeholder
     */
    fun peek(index: Int): T? {
        return itemSnapshotList[index]
    }

    /**
     * Retry any failed load requests that would result in a [LoadState.Error] update to this
     * [LazyPagingItems].
     *
     * Unlike [refresh], this does not invalidate [PagingSource], it only retries failed loads
     * within the same generation of [PagingData].
     *
     * [LoadState.Error] can be generated from two types of load requests:
     * * [PagingSource.load] returning [PagingSource.LoadResult.Error]
     * * [RemoteMediator.load] returning [RemoteMediator.MediatorResult.Error]
     */
    fun retry() {
        pagingDataPresenter.retry()
    }

    /**
     * Refresh the data presented by this [LazyPagingItems].
     *
     * [refresh] triggers the creation of a new [PagingData] with a new instance of [PagingSource]
     * to represent an updated snapshot of the backing dataset. If a [RemoteMediator] is set,
     * calling [refresh] will also trigger a call to [RemoteMediator.load] with [LoadType] [REFRESH]
     * to allow [RemoteMediator] to check for updates to the dataset backing [PagingSource].
     *
     * Note: This API is intended for UI-driven refresh signals, such as swipe-to-refresh.
     * Invalidation due repository-layer signals, such as DB-updates, should instead use
     * [PagingSource.invalidate].
     *
     * @see PagingSource.invalidate
     */
    fun refresh() {
        pagingDataPresenter.refresh()
    }

    /** A [CombinedLoadStates] object which represents the current loading state. */
    public var loadState: CombinedLoadStates by
    mutableStateOf(
        pagingDataPresenter.loadStateFlow.value
            ?: CombinedLoadStates(
                refresh = InitialLoadStates.refresh,
                prepend = InitialLoadStates.prepend,
                append = InitialLoadStates.append,
                source = InitialLoadStates
            )
    )
        private set

    internal suspend fun collectLoadState() {
        pagingDataPresenter.loadStateFlow.filterNotNull().collect { loadState = it }
    }

    internal suspend fun collectPagingData() {
        flow.collectLatest { pagingDataPresenter.collectFrom(it) }
    }
}

private val IncompleteLoadState = LoadState.NotLoading(false)
private val InitialLoadStates =
    LoadStates(LoadState.Loading, IncompleteLoadState, IncompleteLoadState)

/**
 * Collects values from this [Flow] of [PagingData] and represents them inside a [LazyPagingItems]
 * instance. The [LazyPagingItems] instance can be used for lazy foundations such as
 * [LazyListScope.items] in order to display the data obtained from a [Flow] of [PagingData].
 *
 * @sample androidx.paging.compose.samples.PagingBackendSample
 * @param context the [CoroutineContext] to perform the collection of [PagingData] and
 *   [CombinedLoadStates].
 */
@Composable
public fun <T : Any> Flow<PagingData<T>>.collectAsLazyPagingItems(
    context: CoroutineContext = EmptyCoroutineContext
): LazyPagingItems<T> {

    val lazyPagingItems = remember(this) { LazyPagingItems(this) }

    LaunchedEffect(lazyPagingItems) {
        if (context == EmptyCoroutineContext) {
            lazyPagingItems.collectPagingData()
        } else {
            withContext(context) { lazyPagingItems.collectPagingData() }
        }
    }

    LaunchedEffect(lazyPagingItems) {
        if (context == EmptyCoroutineContext) {
            lazyPagingItems.collectLoadState()
        } else {
            withContext(context) { lazyPagingItems.collectLoadState() }
        }
    }

    return lazyPagingItems
}


/**
 * Determines the current state of the LazyPagingItems based on its load state and item count.
 *
 * @receiver The LazyPagingItems instance whose state is being evaluated.
 * @return A [LazyPagingItemsContentState] representing the current state:
 * - [LazyPagingItemsContentState.Error.PrependError] if there is an error in the prepend load state.
 * - [LazyPagingItemsContentState.Error.AppendError] if there is an error in the append load state.
 * - [LazyPagingItemsContentState.Error.RefreshError] if there is an error in the refresh load state.
 * - [LazyPagingItemsContentState.Refreshing] if the refresh load state is currently loading and there are items.
 * - [LazyPagingItemsContentState.Loading] if the refresh load state is currently loading and there are no items.
 * - [LazyPagingItemsContentState.Empty] if there are no items and the load state is idle.
 * - [LazyPagingItemsContentState.NotLoading] if none of the above conditions are met.
 */
val LazyPagingItems<*>.contentState get() = when {
    itemCount != 0 && loadState.prepend is LoadState.Error ->
        Error.PrependError((loadState.prepend as LoadState.Error).error)

    itemCount != 0 && loadState.append is LoadState.Error ->
        Error.AppendError((loadState.append as LoadState.Error).error)

    itemCount != 0 && loadState.refresh is LoadState.Error ->
        Error.RefreshError((loadState.refresh as LoadState.Error).error)

    itemCount != 0 && loadState.refresh is LoadState.Loading -> Refreshing
    itemCount == 0 && loadState.refresh is LoadState.Loading -> Loading
    itemCount == 0 && loadState.isIdle -> Empty
    else -> NotLoading
}


/**
 * Represents the various states of a [LazyPagingItems] instance.
 *
 * This sealed interface is used to determine the current state of the paging items,
 * which can be one of the following:
 * - [Loading]: Indicates that the data is currently being loaded.
 * - [Refreshing]: Indicates that the data is being refreshed.
 * - [Empty]: Indicates that there are no items to display.
 * - [NotLoading]: Indicates that the data is not currently loading or refreshing.
 * - [Error]: Represents an error state with specific error types for different loading stages.
 */
sealed interface LazyPagingItemsContentState {

    /** Indicates that the data is currently being loaded. */
    data object Loading : LazyPagingItemsContentState

    /** Indicates that the data is being refreshed. */
    data object Refreshing : LazyPagingItemsContentState

    /** Indicates that there are no items to display. */
    data object Empty : LazyPagingItemsContentState

    /** Indicates that the data is not currently loading or refreshing. */
    data object NotLoading : LazyPagingItemsContentState

    /**
     * Represents an error state with specific error types for different loading stages.
     *
     * @property exception The exception that caused the error.
     */
    sealed class Error(open val exception: Throwable) : LazyPagingItemsContentState {

        /**
         * Represents an error that occurred during the refresh stage.
         *
         * @property exception The exception that caused the refresh error.
         */
        data class RefreshError(override val exception: Throwable) : Error(exception)

        /**
         * Represents an error that occurred during the append stage.
         *
         * @property exception The exception that caused the append error.
         */
        data class AppendError(override val exception: Throwable) : Error(exception)

        /**
         * Represents an error that occurred during the prepend stage.
         *
         * @property exception The exception that caused the prepend error.
         */
        data class PrependError(override val exception: Throwable) : Error(exception)
    }
}