package me.stayplus.paypaytest.data.persistance

import android.content.Context

class AppPreferencesManager(
    context: Context
) {

    private val sharedPreferences = context.getSharedPreferences(
        "${context.applicationInfo.packageName}.preferences",
        Context.MODE_PRIVATE
    )

    var lastUpdateMillis: Long
        set(value) {
            sharedPreferences.edit().apply {
                putLong(ARG_LAST_UPDATE, value)
            }.apply()
        }
        get() {
            return sharedPreferences.getLong(ARG_LAST_UPDATE, -1)
        }

    companion object {
        private const val ARG_LAST_UPDATE = "lastUpdate"
    }

}