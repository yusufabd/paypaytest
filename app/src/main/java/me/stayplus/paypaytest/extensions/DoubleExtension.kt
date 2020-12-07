package me.stayplus.paypaytest.extensions

fun Double.formatDecimal(): String {
    return String.format("%.2f", this)
}