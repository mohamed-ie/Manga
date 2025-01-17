package com.manga.feature.more.more

import androidx.lifecycle.ViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class MoreViewModel : ViewModel() {

}

data class MoreUiState(
    val isLoading: Boolean = false
)
