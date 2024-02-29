package com.example.deloitte_android_test.utils

import androidx.annotation.DimenRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.deloitte_android_test.R
import kotlinx.coroutines.launch

@Composable
fun ShowProgressBar() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            strokeWidth = 5.dp,
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun DisplayMessage(
    message: String,
    showSb: Boolean,
    openSnackBar: (Boolean) -> Unit
) {

    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()

    SnackbarHost(
        modifier = Modifier,
        hostState = snackState
    ) {
        Snackbar(
            snackbarData = it,
            containerColor = Color.Black,
            contentColor = Color.White
        )
    }
    if (showSb) {
        LaunchedEffect(Unit) {
            snackScope.launch { snackState.showSnackbar(message) }
            openSnackBar(false)
        }

    }
}

@Composable
fun ItemImageFromURLWithPlaceHolder(imageUrl: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.baseline_add_shopping_cart_24),
        contentDescription = stringResource(R.string.item_image),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(160.dp)
            .padding(16.dp),

        )
}

@Composable
@ReadOnlyComposable
fun fontDimensionResource(@DimenRes id: Int) = dimensionResource(id = id).value.sp
