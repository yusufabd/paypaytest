package me.stayplus.paypaytest.domain.entities

data class Quote(
    val currencyFrom: String,
    val currencyTo: String,
    val rate: Double
)