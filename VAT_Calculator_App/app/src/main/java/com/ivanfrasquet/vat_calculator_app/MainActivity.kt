package com.ivanfrasquet.vat_calculator_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ivanfrasquet.vat_calculator_app.ui.ProductLayout
import com.ivanfrasquet.vat_calculator_app.ui.theme.VAT_Calculator_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            VAT_Calculator_AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    ProductLayout()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {
    VAT_Calculator_AppTheme {
        Surface(modifier = Modifier.fillMaxSize(),
        ) {
            ProductLayout()
        }
    }
}