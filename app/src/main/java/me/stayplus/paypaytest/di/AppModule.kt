package me.stayplus.paypaytest.di

import me.stayplus.paypaytest.data.persistance.AppPreferencesManager
import me.stayplus.paypaytest.presentation.utils.ResourceManager
import org.koin.dsl.module

val appModule = module {

    single { ResourceManager(get()) }

    single { AppPreferencesManager(get()) }
}