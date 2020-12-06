package me.stayplus.paypaytest.data.repository

import com.google.gson.Gson
import me.stayplus.paypaytest.data.network.AppNetworkService
import me.stayplus.paypaytest.domain.entities.Currency
import me.stayplus.paypaytest.domain.entities.Quote

class MainRepository(
    private val api: AppNetworkService,
    private val gson: Gson
) {

    suspend fun getCurrencyList(): List<Currency> {
        val data = api.getCurrencyList()
        val map = gson.fromJson(data.currencies, HashMap::class.java) as Map<String, String>
        return map.map { Currency(it.key, it.value) }
    }

    suspend fun getQuotes(): List<Quote> {
        val data = api.getQuotes()
        val map = gson.fromJson(data.quotes, HashMap::class.java) as Map<String, Double>
        return map.map { getAsQuote(it.key, it.value) }
    }

    private fun getAsQuote(currencies: String, rate: Double): Quote {
        val from = currencies.substring(0, 3)
        val to = currencies.substring(3, currencies.length)
        return Quote(from, to, rate)
    }

}