package com.ivanfrasquet.vat_calculator_app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.ivanfrasquet.vat_calculator_app.R
import com.ivanfrasquet.vat_calculator_app.ui.theme.VAT_Calculator_AppTheme
import java.text.DecimalFormat

@Composable
fun ProductLayout() {
    var amountInput by rememberSaveable { mutableStateOf("") }
    var percentageInput by rememberSaveable { mutableStateOf("") }
    var productName by rememberSaveable { mutableStateOf("") }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val percentage = (percentageInput.toDoubleOrNull() ?: 0.0)
    val vat = calculateVat(amount, percentage)

    Scaffold (
        topBar = {
            VAT_Calculator_App()
        }
    ){ it ->
        Column(
            modifier = Modifier
                .padding(it)
                .statusBarsPadding()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                .verticalScroll(rememberScrollState())
                .safeDrawingPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.product),
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier
                    .padding(bottom = dimensionResource(id = R.dimen.padding_medium), top = dimensionResource(id = R.dimen.padding_medium))
                    .align(alignment = Alignment.Start)
            )
            EditField(
                value = productName,
                onValueChanged = { nuevoTexto ->
                    productName = nuevoTexto
                },
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .padding(bottom =  dimensionResource(id = R.dimen.padding_small))
                    .fillMaxWidth(),
                label = R.string.product_name,
                leadingIcon = R.drawable.outline_storefront_24,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            EditField(
                value = amountInput,
                onValueChanged = { amountInput = it },
                modifier = Modifier.padding(bottom =  dimensionResource(id = R.dimen.padding_small)).fillMaxWidth().clip(MaterialTheme.shapes.small),
                label = R.string.price,
                leadingIcon = R.drawable.money,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            EditField(
                value = percentageInput,
                onValueChanged = { percentageInput = it },
                modifier = Modifier.padding(bottom =  dimensionResource(id = R.dimen.padding_large)).fillMaxWidth().clip(MaterialTheme.shapes.small),
                label = R.string.vat,
                leadingIcon = R.drawable.percent,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
            Text(
                text = stringResource(R.string.total_price, vat),
                style = MaterialTheme.typography.displayMedium
            )
            Spacer(modifier = Modifier.height(150.dp))
            }
        }

}

private fun calculateVat(amount: Double, vatPercent: Double = 15.0): String {
    val vat = vatPercent / 100 * amount + amount
    val decimalFormat = DecimalFormat("#0.00")
    return decimalFormat.format(vat)
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
                        .padding(bottom =  dimensionResource(id = R.dimen.padding_large))
                        .size(60.dp),
                    contentScale = ContentScale.Fit
                )
                Text (
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge,
                )
            }
        },
        modifier = modifier
    )
}