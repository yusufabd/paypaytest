package me.stayplus.paypaytest.data.repository

import com.google.gson.Gson
import me.stayplus.paypaytest.data.network.AppNetworkService
import me.stayplus.paypaytest.domain.entities.Currency

class MainRepository(
    private val api: AppNetworkService,
    private val gson: Gson
) {

    suspend fun getCurrencyList(): List<Currency> {
        val data = api.getCurrencyList()
        val map = gson.fromJson(data.currencies, HashMap::class.java) as Map<String, String>
        return map.map { Currency(it.key, it.value) }
    }

}