package me.stayplus.paypaytest.data.models
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName


data class CurrencyListResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("terms")
    val terms: String,
    @SerializedName("privacy")
    val privacy: String,
    @SerializedName("currencies")
    val currencies: JsonObject
)