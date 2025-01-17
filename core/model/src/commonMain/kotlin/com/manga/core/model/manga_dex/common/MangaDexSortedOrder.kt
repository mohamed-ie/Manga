package com.manga.core.model.manga_dex.common

data class MangaDexSortedOrder<T : MangaDexOrder>(
    val order: T,
    val mangaDexSortOrder: MangaDexSortOrder = MangaDexSortOrder.DESC
)

val <T : MangaDexOrder> T.descOrder get() = MangaDexSortedOrder(this, MangaDexSortOrder.DESC)

val <T : MangaDexOrder> T.ascOrder get() = MangaDexSortedOrder(this, MangaDexSortOrder.ASC)