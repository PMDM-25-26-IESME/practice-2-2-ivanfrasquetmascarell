package com.ivanfrasquet.vat_calculator_app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ivanfrasquet.vat_calculator_app.R
import com.ivanfrasquet.vat_calculator_app.ui.ViewModels.VatCalculatorViewModel

@Composable
fun ProductLayout(
    vatCalculatorViewModel: VatCalculatorViewModel = viewModel()
) {
    Scaffold (
        topBar = {
            VAT_Calculator_App()
        }
    ){ it ->
        val vatCalculatorUiState by vatCalculatorViewModel.uiState.collectAsState()
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                .verticalScroll(rememberScrollState())
                .safeDrawingPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.product),
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier
                    .padding(bottom = dimensionResource(id = R.dimen.padding_medium), top = dimensionResource(id = R.dimen.padding_large))
                    .align(alignment = Alignment.Start)
            )
            EditField(
                value = vatCalculatorUiState.productInput,
                onValueChanged = { vatCalculatorViewModel.updateProductInput(it)},
                modifier = Modifier
                    .padding(bottom = dimensionResource(id = R.dimen.padding_small))
                    .clip(MaterialTheme.shapes.small)
                    .fillMaxWidth(),
                label = R.string.product_name,
                leadingIcon = R.drawable.outline_storefront_24,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            EditField(
                value = vatCalculatorUiState.amountInput,
                onValueChanged = { vatCalculatorViewModel.updateAmountInput(it) },
                modifier = Modifier
                    .padding(bottom = dimensionResource(id = R.dimen.padding_small))
                    .clip(MaterialTheme.shapes.small)
                    .fillMaxWidth(),
                label = R.string.price,
                leadingIcon = R.drawable.money,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            EditField(
                value = vatCalculatorUiState.percentInput,
                onValueChanged = { vatCalculatorViewModel.updateVatInput(it) },
                modifier = Modifier
                    .padding(bottom = dimensionResource(id = R.dimen.padding_large))
                    .clip(MaterialTheme.shapes.small)
                    .fillMaxWidth(),
                label = R.string.vat,
                leadingIcon = R.drawable.percent,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
            Text(
                text = stringResource(R.string.total_price, vatCalculatorUiState.vat),
                style = MaterialTheme.typography.displayMedium
            )
            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VAT_Calculator_App(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size)),
                    contentScale = ContentScale.Fit
                )
                Text (
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayMedium,
                )
            }
        },
        modifier = modifier
    )
}