package core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImagePainter.Companion.DefaultTransform
import coil3.compose.AsyncImagePainter.State
import coil3.compose.DefaultModelEqualityDelegate
import coil3.compose.EqualityDelegate
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageScope

@Composable
fun MangaSubComposeAsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    transform: (State) -> State = DefaultTransform,
    loading: @Composable (SubcomposeAsyncImageScope.(State.Loading) -> Unit)? = {
        Box(Modifier.fillMaxSize().shimmer(true))
    },
    success: @Composable (SubcomposeAsyncImageScope.(State.Success) -> Unit)? = null,
    error: @Composable (SubcomposeAsyncImageScope.(State.Error) -> Unit)? = null,
    onLoading: ((State.Loading) -> Unit)? = null,
    onSuccess: ((State.Success) -> Unit)? = null,
    onError: ((State.Error) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
    modelEqualityDelegate: EqualityDelegate = DefaultModelEqualityDelegate
) = SubcomposeAsyncImage(
    model = model,
    contentDescription = contentDescription,
    modifier = modifier,
    transform = transform,
    loading = loading,
    success = success,
    error = error,
    onLoading = onLoading,
    onSuccess = onSuccess,
    onError = onError,
    alignment = alignment,
    contentScale = contentScale,
    alpha = alpha,
    colorFilter = colorFilter,
    filterQuality = filterQuality,
    modelEqualityDelegate = modelEqualityDelegate
)