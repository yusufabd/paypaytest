package me.stayplus.paypaytest.domain.interactor

import me.stayplus.paypaytest.data.repository.MainRepository
import me.stayplus.paypaytest.domain.entities.Currency

class MainInteractor(private val repository: MainRepository) {

    suspend fun getCurrencyQuotes(): List<Currency>{
        return repository.getCurrencyList()
    }

}