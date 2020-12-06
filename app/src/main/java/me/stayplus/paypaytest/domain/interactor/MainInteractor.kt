package me.stayplus.paypaytest.domain.interactor

import me.stayplus.paypaytest.data.repository.MainRepository
import me.stayplus.paypaytest.domain.entities.Currency
import me.stayplus.paypaytest.domain.entities.Quote

class MainInteractor(private val repository: MainRepository) {

    suspend fun getCurrencyList(): List<Currency>{
        return repository.getCurrencyList()
    }

    suspend fun getQuotes(): List<Quote> {
        return repository.getQuotes()
    }

}