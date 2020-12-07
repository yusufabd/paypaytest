package me.stayplus.paypaytest.domain.interactor

import me.stayplus.paypaytest.data.persistance.AppPreferencesManager
import me.stayplus.paypaytest.data.repository.MainRepository
import me.stayplus.paypaytest.domain.entities.Currency
import me.stayplus.paypaytest.domain.entities.Quote
import java.util.*

class MainInteractor(
    private val repository: MainRepository,
    private val preferencesManager: AppPreferencesManager
    ) {

    suspend fun getCurrencyList(): List<Currency>{
        return repository.getCurrencyList()
    }

    suspend fun getQuotes(): List<Quote> {
        return repository.getQuotes()
    }

    private fun isQuotesExpired(): Boolean {
        val lastUpdateTimeInMillis = preferencesManager.lastUpdateMillis
        if (lastUpdateTimeInMillis == -1L)
            return true

        val currentTimeInMillis = Date().time
        return currentTimeInMillis > lastUpdateTimeInMillis + cacheTimeMillis
    }

    fun calculateQuote(amount: Double, currency: Currency) {

    }

    companion object {
        private const val cacheTimeMillis: Long = 30 * 60 * 1000
    }

}