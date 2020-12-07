package me.stayplus.paypaytest.domain.entities

data class Quote(
    val currencyFrom: String,
    val currencyTo: String,
    val rate: Double,
    val amount: Double = 0.0
) {
    fun calculateQuote(): Double {
        return amount * rate
    }
}