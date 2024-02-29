package com.example.deloitte_android_test.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.deloitte_android_test.R
import com.example.deloitte_android_test.presentation.viewmodel.DetailsViewModel
import com.example.deloitte_android_test.presentation.viewstate.ItemViewState
import com.example.deloitte_android_test.utils.DataHandler
import com.example.deloitte_android_test.utils.DisplayMessage
import com.example.deloitte_android_test.utils.ItemImageFromURLWithPlaceHolder
import com.example.deloitte_android_test.utils.ShowProgressBar
import com.example.deloitte_android_test.utils.fontDimensionResource

@Composable
fun DetailsScreen(
    id: String,
    viewModel: DetailsViewModel = hiltViewModel(),
    onShowMainScreen: () -> Unit
) {

    var openSnackBar by remember { mutableStateOf(false) }
    var snackBarMessage by remember { mutableStateOf("") }

    val handler by viewModel.result.collectAsStateWithLifecycle(
        initialValue = DataHandler.SUCCESS(
            ItemViewState()
        )
    )
    val showDialog = remember { mutableStateOf(true) }

    var selectedProduct by remember { mutableStateOf(ItemViewState()) }
    if (showDialog.value) {
        DetailsContentDialog(itemViewState = selectedProduct) {
            showDialog.value = it
            onShowMainScreen()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getItemData(id)
    }

    when (handler) {
        is DataHandler.LOADING -> {
            ShowProgressBar()
        }

        is DataHandler.SUCCESS -> {
            handler.data?.let {
                selectedProduct = it

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
fun DetailsContentDialog(
    itemViewState: ItemViewState,
    setShowDialog: (Boolean) -> Unit
) {


    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = DialogProperties(usePlatformDefaultWidth = false),

        ) {
        Surface(
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.space_small)),
            color = Color.White,
        ) {
            Column {
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.space_large))
                        .fillMaxWidth()
                        .wrapContentHeight(),
                ) {
                    DetailsContent(itemViewState, setShowDialog)
                }

                DetailsContentButtons()
            }

        }
    }
}

@Composable
fun DetailsContent(itemViewState: ItemViewState, setShowDialog: (Boolean) -> Unit) {
    Column {
        DetailsContentHeader(setShowDialog)
        Divider(color = Color.LightGray, thickness = dimensionResource(id = R.dimen.space_xxxsmall))
        DetailsContentInfo(itemViewState)

    }
}

@Composable
fun DetailsContentButtons() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = dimensionResource(id = R.dimen.space_xx_large))
            .background(Color.LightGray)) {

        Button(colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red,
            contentColor = Color.White),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.space_large)),
            modifier = Modifier.padding(dimensionResource(id = R.dimen.space_large)),
            onClick = { /*TODO*/ }) {
            Text(
                text = stringResource(R.string.wish_list),
                fontSize = fontDimensionResource(id = R.dimen.space_small),
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.space_medium),
                    vertical = dimensionResource(id = R.dimen.space_small))
            )
        }

        Button(colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red,
            contentColor = Color.White),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.space_large)),
            modifier = Modifier.padding(dimensionResource(id = R.dimen.space_large)),
            onClick = { /*TODO*/ }) {
            Text(
                text = stringResource(R.string.add_to_cart),
                fontSize = fontDimensionResource(id = R.dimen.space_small),
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.space_medium),
                    vertical = dimensionResource(id = R.dimen.space_small))
            )
        }

    }
}

@Composable
fun DetailsContentHeader(setShowDialog: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        IconButton(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.space_extra_small))
                .size(dimensionResource(id = R.dimen.space_large)),
            onClick = { setShowDialog(false) }
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = stringResource(R.string.close_button_icon),
                tint = Color.Red
            )
        }

        Text(
            text = stringResource(R.string.product_detail),
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.space_large)),
            color = Color.Red,
            style = MaterialTheme.typography.headlineSmall
        )


    }
}


@Composable
fun DetailsContentInfo(itemViewState: ItemViewState) {

    Column {

        Row {

            ItemImageFromURLWithPlaceHolder(itemViewState.image)


            Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.space_medium))) {
                Text(
                    text = stringResource(R.string.dollar_symbol, itemViewState.price),
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = stringResource(R.string.dollar_symbol, itemViewState.oldPrice),
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )

                Text(
                    text = stringResource(R.string.in_stock),
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.space_extra_large)),
                    color = Color.Red
                )


            }


        }
        Column(
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Divider(color = Color.LightGray, thickness = dimensionResource(id = R.dimen.space_xxxsmall))
            Text(
                text = stringResource(R.string.name),
                fontSize = fontDimensionResource(id = R.dimen.space_medium),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = itemViewState.name,
                fontSize = fontDimensionResource(id = R.dimen.space_medium),
                color = Color.LightGray
            )
            Divider(color = Color.LightGray, thickness = dimensionResource(id = R.dimen.space_xxxsmall))
            Text(
                text = stringResource(R.string.category),
                fontSize = fontDimensionResource(id = R.dimen.space_medium),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = itemViewState.category,
                fontSize = fontDimensionResource(id = R.dimen.space_medium),
                color = Color.LightGray
            )
            Divider(color = Color.LightGray, thickness = dimensionResource(id = R.dimen.space_xxxsmall))
            Text(
                text = stringResource(R.string.amount_in_stocks),
                fontSize = fontDimensionResource(id = R.dimen.space_medium),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = itemViewState.stock.toString(),
                fontSize = fontDimensionResource(id = R.dimen.space_medium),
                color = Color.LightGray
            )
        }
    }
}

@Preview(widthDp = 150, heightDp = 50)
@Composable
fun DetailsContentButtonsPreview(){
    DetailsContentButtons()
}

