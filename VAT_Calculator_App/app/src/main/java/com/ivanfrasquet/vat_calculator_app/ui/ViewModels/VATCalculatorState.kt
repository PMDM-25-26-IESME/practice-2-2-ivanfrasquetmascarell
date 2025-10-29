package com.ivanfrasquet.vat_calculator_app.ui.ViewModels

data class VatCalCulatorState (
    val productInput: String = "",
    val amountInput: String = "",
    val percentInput: String = "15",
    val vat: String = ""
)