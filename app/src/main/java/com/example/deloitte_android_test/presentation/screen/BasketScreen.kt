package com.example.deloitte_android_test.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.deloitte_android_test.R
import com.example.deloitte_android_test.presentation.viewmodel.ItemViewModel
import com.example.deloitte_android_test.presentation.viewstate.ItemViewState
import com.example.deloitte_android_test.utils.DataHandler
import com.example.deloitte_android_test.utils.DismissBackground
import com.example.deloitte_android_test.utils.DisplayMessage
import com.example.deloitte_android_test.utils.ShowProgressBar
import com.example.deloitte_android_test.utils.fontDimensionResource
import com.example.deloitte_android_test.utils.shakeClickEffect
import kotlinx.coroutines.delay


@Composable
fun BasketScreen(
    viewModel: ItemViewModel = hiltViewModel()
) {
    var openSnackBar by remember { mutableStateOf(false) }
    var snackBarMessage by remember { mutableStateOf("") }

    val handler by viewModel.basketItems.collectAsStateWithLifecycle(
        initialValue = DataHandler.SUCCESS(
            Pair(listOf(), 0.0)
        )
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.getBasketItems()
    }

    when (handler) {
        is DataHandler.LOADING -> {
            ShowProgressBar()
        }

        is DataHandler.SUCCESS -> {
            handler.data?.let {
                BasketContent(items = it.first, itemsCost = it.second) { itemViewState ->
                    viewModel.removeItemFromCart(itemViewState = itemViewState)
                }
            }

        }

        is DataHandler.ERROR -> {
            openSnackBar = true
            snackBarMessage = "Error loading data...!"
            DisplayMessage(snackBarMessage, openSnackBar) { openSnackBar = it }
        }

    }

}


@Composable
fun BasketContent(
    items: List<ItemViewState>,
    itemsCost: Double,
    onRemove: (ItemViewState) -> Unit
) {

    Column {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.space_medium))) {
            Text(
                text = stringResource(R.string.basket),
                color = Color.Red,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.space_large))
            )
            Divider(
                color = Color.LightGray,
                thickness = dimensionResource(id = R.dimen.space_xxxsmall)
            )

            LazyColumn {
                items(
                    count = items.size,
                    key = { index -> items[index].productId },
                    itemContent = { index ->
                        val result = items[index]
                        BasketItemView(
                            result = result,
                            onRemove = onRemove
                        )
                    }
                )
            }

            Divider(
                color = Color.LightGray,
                thickness = dimensionResource(id = R.dimen.space_xxxsmall)
            )

        }

        Spacer(modifier = Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.LightGray)
        ) {

            Button(colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White,
                disabledContainerColor = Color.White,
                disabledContentColor = Color.Black
            ),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.space_large)),
                modifier = Modifier
                    .shakeClickEffect()
                    .padding(dimensionResource(id = R.dimen.space_large)),
                onClick = {}
            ) {
                Text(
                    text = stringResource(R.string.checkout),
                    fontSize = fontDimensionResource(id = R.dimen.space_small),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.space_medium),
                        vertical = dimensionResource(id = R.dimen.space_small)
                    )
                )
            }
            Column {

                Text(
                    text = stringResource(R.string.dollar_symbol, itemsCost),
                    fontWeight = FontWeight.Bold,
                    fontSize = fontDimensionResource(id = R.dimen.space_large),
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.space_large))

                )
                Text(
                    text = "Total",
                    fontWeight = FontWeight.Bold,
                    fontSize = fontDimensionResource(id = R.dimen.space_small),
                )

            }


        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketItemView(
    result: ItemViewState,
    onRemove: (ItemViewState) -> Unit
) {
    var show by remember { mutableStateOf(true) }
    val currentItem by rememberUpdatedState(result)
    val dismissState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                show = false
                true
            } else false
        }, positionalThreshold = { 150.dp.toPx() }
    )
    AnimatedVisibility(
        show, exit = fadeOut(spring())
    ) {
        SwipeToDismiss(
            state = dismissState,
            modifier = Modifier,
            background = {
                DismissBackground(dismissState)
            },
            dismissContent = {
                BasketItemViewContent(result)
            }
        )
    }

    LaunchedEffect(show) {
        if (!show) {
            delay(800)
            onRemove(currentItem)
        }
    }


}

@Composable
private fun BasketItemViewContent(
    result: ItemViewState
) {
    Column {
        Divider(
            color = Color.LightGray,
            thickness = dimensionResource(id = R.dimen.space_xxxsmall)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(result.image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.baseline_add_shopping_cart_24),
                contentDescription = stringResource(R.string.item_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.space_medium))
                    .size(dimensionResource(id = R.dimen.space_extra_large))
                    .clip(CircleShape)
                    .border(
                        dimensionResource(id = R.dimen.space_xxsmall),
                        Color.LightGray,
                        CircleShape
                    ),

                )

            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.space_medium))
                    .alignBy(LastBaseline)
            ) {
                Text(
                    text = result.name,
                    fontWeight = FontWeight.Bold,

                    )

                Text(
                    text = stringResource(R.string.dollar_symbol, result.price),
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.space_medium))
                )

            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_large)))

            Text(
                text = stringResource(R.string.qty, result.quantity),
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                modifier = Modifier.alignBy(LastBaseline)
            )

        }
    }

}
