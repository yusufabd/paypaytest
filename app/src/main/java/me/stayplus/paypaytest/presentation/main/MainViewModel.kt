package me.stayplus.paypaytest.presentation.main

import androidx.lifecycle.*
import kotlinx.coroutines.*
import me.stayplus.paypaytest.BuildConfig
import me.stayplus.paypaytest.domain.entities.Currency
import me.stayplus.paypaytest.domain.interactor.MainInteractor
import me.stayplus.paypaytest.extensions.isInternetError
import me.stayplus.paypaytest.presentation.utils.ResourceManager
import me.stayplus.paypaytest.presentation.utils.SingleEventLiveData

class MainViewModel(
    private val interactor: MainInteractor,
    private val resourceManager: ResourceManager
) : ViewModel(), LifecycleObserver {

    val currencyList: LiveData<List<Currency>>
        get() = _currencyList
    val showProgress: LiveData<Boolean>
        get() = _showProgress
    val getCurrencyQuotesError: LiveData<String>
        get() = _getCurrencyQuotesError

    private val _currencyList = MutableLiveData<List<Currency>>()
    private val _showProgress = MutableLiveData<Boolean>()
    private val _getCurrencyQuotesError = SingleEventLiveData<String>()

    private val job = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val getCurrencyQuotesErrorHandler = CoroutineExceptionHandler { context, throwable ->
        handleCoroutineException(_getCurrencyQuotesError, throwable)
        _showProgress.value = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getCurrencyQuotes() {
        _showProgress.value = true
        (uiScope + getCurrencyQuotesErrorHandler).launch {
            val list = interactor.getCurrencyQuotes()
            _currencyList.value = list
            _showProgress.value = false
        }
    }

    private fun handleCoroutineException(
        liveData: MutableLiveData<String>,
        throwable: Throwable
    ) {
        liveData.value = if (throwable.isInternetError()) {
            resourceManager.getString(me.stayplus.paypaytest.R.string.error_slow_or_no_internet_connection)
        } else {
            resourceManager.getString(me.stayplus.paypaytest.R.string.error_something_went_wrong)
        }

        if (BuildConfig.DEBUG)
            throwable.printStackTrace()
    }
}