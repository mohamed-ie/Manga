package core.common.com.manga.core.common

data class Pageable<T,K>(
    val data: List<T>,
    val totalCount:Int,
    val nextKey : K? = null,
    val previousKey : K? = null
)