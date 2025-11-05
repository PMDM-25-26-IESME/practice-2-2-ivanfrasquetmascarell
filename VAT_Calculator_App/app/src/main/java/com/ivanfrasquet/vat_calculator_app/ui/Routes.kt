package com.ivanfrasquet.vat_calculator_app.ui

import androidx.annotation.StringRes
import com.ivanfrasquet.vat_calculator_app.R

enum class Routes(@StringRes val title: Int) {
    Start (title = R.string.app_name),
    VatResult (title = R.string.vat_result)
}