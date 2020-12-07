package me.stayplus.paypaytest.di

import me.stayplus.paypaytest.data.repository.MainRepository
import me.stayplus.paypaytest.domain.interactor.MainInteractor
import me.stayplus.paypaytest.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val screenModule = module {
    factory { MainRepository(get(), get()) }
    single { MainInteractor(get(), get()) }
    viewModel { MainViewModel(get(), get()) }
}