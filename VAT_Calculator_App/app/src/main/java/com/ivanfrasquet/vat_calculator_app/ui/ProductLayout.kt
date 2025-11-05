package com.ivanfrasquet.vat_calculator_app.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ivanfrasquet.vat_calculator_app.R
import com.ivanfrasquet.vat_calculator_app.ui.ViewModels.VatCalculatorViewModel

@Composable
fun ProductLayout(
    vatViewModel: VatCalculatorViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = Routes.valueOf(
        backStackEntry?.destination?.route ?: Routes.Start.name
    )
    val vatCalculatorUiState by vatViewModel.uiState.collectAsState()

    Scaffold (
        topBar = {
            VatCalculatorTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                showShare = currentScreen == Routes.VatResult,
                onShareClicked = { createShareIntent(context, product= vatCalculatorUiState.productInput, price = vatCalculatorUiState.amountInput, total = vatCalculatorUiState.vat) }
            )
        }
    ) { innerPadding ->
        val vatCalculatorUiState by vatViewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = Routes.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable( route = Routes.Start.name) {
                VatStartScreen(
                    vatViewModel = vatViewModel,
                    onNextButtonClicked = {navController.navigate(Routes.VatResult.name)},
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(40.dp)
                )
            }
            composable(route = Routes.VatResult.name) {
                VatResultScreen(
                    vatViewModel = vatViewModel,
                    onBackButtonClicked = { navController.navigate(Routes.Start.name) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(40.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VatCalculatorTopAppBar(
    currentScreen: Routes,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    showShare: Boolean = false,
    onShareClicked: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "logo",
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.image_size))
                        .size(60.dp),
                    contentScale = ContentScale.Fit
                )
                Text (
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge,
                )
            }
        },
        navigationIcon = {
            if (canNavigateBack && currentScreen != Routes.valueOf(Routes.Start.name)) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
        actions = {
            if (showShare) {
                IconButton(onClick = onShareClicked) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = stringResource(R.string.share)
                    )
                }
            }
        },
        modifier = modifier
    )
}

private fun createShareIntent(context: Context, product: String, price: String, total: String) {
    val shareText = context.getString(R.string.vat_amount_total_bill, product, price, total)
    // Create an ACTION_SEND implicit intent with order details in the intent extras
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.your_vat))
        putExtra(Intent.EXTRA_TEXT, shareText)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.your_vat)
        )
    )
}