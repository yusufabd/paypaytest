package me.stayplus.paypaytest

import android.app.Application
import me.stayplus.paypaytest.di.appModule
import me.stayplus.paypaytest.di.networkingModule
import me.stayplus.paypaytest.di.screenModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PayPayApp : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PayPayApp)
            modules(listOf(appModule, networkingModule, screenModule))
        }
    }

    companion object {
        const val BASE_URL = "http://api.currencylayer.com/"
    }

}