package me.stayplus.paypaytest.data.models

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName


data class QuotesResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("terms")
    val terms: String,
    @SerializedName("privacy")
    val privacy: String,
    @SerializedName("timestamp")
    val timestamp: Int,
    @SerializedName("source")
    val source: String,
    @SerializedName("quotes")
    val quotes: JsonObject
)