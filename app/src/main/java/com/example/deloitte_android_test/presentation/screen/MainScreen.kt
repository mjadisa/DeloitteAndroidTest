package com.example.deloitte_android_test.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.deloitte_android_test.R
import com.example.deloitte_android_test.presentation.viewmodel.MainViewModel
import com.example.deloitte_android_test.presentation.viewstate.ItemListViewState
import com.example.deloitte_android_test.presentation.viewstate.ItemViewState
import com.example.deloitte_android_test.utils.DataHandler
import com.example.deloitte_android_test.utils.DisplayMessage
import com.example.deloitte_android_test.utils.ItemImageFromURLWithPlaceHolder
import com.example.deloitte_android_test.utils.ShowProgressBar


@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onShowDetailsScreen: (Int) -> Unit
) {
    var openSnackBar by remember { mutableStateOf(false) }
    var snackBarMessage by remember { mutableStateOf("") }

    val handler by viewModel.result.collectAsStateWithLifecycle(
        initialValue = DataHandler.SUCCESS(
            ItemListViewState()
        )
    )

    var selectedProductId by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = Unit) {
        viewModel.getData()
    }

    when (handler) {
        is DataHandler.LOADING -> {
            ShowProgressBar()
        }

        is DataHandler.SUCCESS -> {
            handler.data?.let {
                Column {
                    ShowItemsTitle()
                    ShowItemList(it,
                        onItemClick = { itemViewState ->
                            selectedProductId = itemViewState.productId.toInt()
                            viewModel.cacheSelectedItem(itemViewState)
                            onShowDetailsScreen(selectedProductId)

                        })
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
fun ShowItemsTitle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = stringResource(R.string.catalogue), Modifier.padding(dimensionResource(id = R.dimen.space_medium)), color = Color.Red,
            style = MaterialTheme.typography.headlineSmall
        )

    }


}


@Composable
fun ShowItemList(
    itemListViewState: ItemListViewState,
    onItemClick: (ItemViewState) -> Unit
) {
    itemListViewState.itemsViewState.let { results ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = dimensionResource(id = R.dimen.space_xx_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.space_large)),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.space_medium)),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                count = results.size,
                key = { index -> results[index].productId },
                itemContent = { index ->
                    val result = results[index]
                    ItemView(
                        onClick = onItemClick,
                        result = result
                    )
                }
            )
        }

    }

}


@Composable
fun ItemView(
    result: ItemViewState,
    onClick: (ItemViewState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.space_medium), horizontal = dimensionResource(id = R.dimen.space_small))
            .clickable {
                onClick.invoke(result)
            }
    ) {
        Card(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.space_medium)),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.space_small)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = dimensionResource(id = R.dimen.space_small)
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            )


        ) {
            ItemImageFromURLWithPlaceHolder(result.image)

            Text(text = result.name, color = Color.Gray, modifier = Modifier.padding(dimensionResource(id = R.dimen.space_small)))
            Text(
                text = stringResource(R.string.dollar_symbol, result.price),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.space_small))
            )

        }
    }

}
