package me.stayplus.paypaytest.data.network

import me.stayplus.paypaytest.data.models.CurrencyListResponse
import me.stayplus.paypaytest.data.models.QuotesResponse
import retrofit2.http.GET

interface AppNetworkService {

    @GET("list")
    suspend fun getCurrencyList(): CurrencyListResponse

    @GET("live")
    suspend fun getQuotes(): QuotesResponse

}