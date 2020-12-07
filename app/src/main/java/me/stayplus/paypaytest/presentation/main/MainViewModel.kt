package me.stayplus.paypaytest.presentation.main

import androidx.lifecycle.*
import kotlinx.coroutines.*
import me.stayplus.paypaytest.BuildConfig
import me.stayplus.paypaytest.domain.entities.Currency
import me.stayplus.paypaytest.domain.entities.Quote
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
    val currentQuotes: LiveData<List<Quote>>
        get() = _currentQuotes
    val showProgress: LiveData<Boolean>
        get() = _showProgress
    val getCurrencyQuotesError: LiveData<String>
        get() = _getCurrencyQuotesError

    private val _currencyList = MutableLiveData<List<Currency>>()
    private val _showProgress = MutableLiveData<Boolean>()
    private val _getCurrencyQuotesError = SingleEventLiveData<String>()
    private val _quotes = MutableLiveData<List<Quote>>()
    private val _currentQuotes = MutableLiveData<List<Quote>>()

    private val job = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private var amount: Double = 0.0

    private val getCurrencyQuotesErrorHandler = CoroutineExceptionHandler { context, throwable ->
        handleCoroutineException(_getCurrencyQuotesError, throwable)
        _showProgress.value = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getCurrencyQuotes() {
        _showProgress.value = true
        (uiScope + getCurrencyQuotesErrorHandler).launch {
            val currencies = async { interactor.getCurrencyList() }
            val quotes = async { interactor.getQuotes() }
            val data = awaitAll(currencies, quotes)
            _currencyList.value = data.first() as List<Currency>
            _quotes.value = data.last() as List<Quote>
            _showProgress.value = false
        }
    }

    fun onCurrencySelected(position: Int) {
        _currencyList.value?.let {
            val currency = it[position]
            val quotes = _quotes.value!!
            _currentQuotes.value = quotes
                .map { it.copy(amount = amount) }
        }
    }

    fun onAmountEntered(string: String?) {
        amount = string?.toDouble() ?: 0.0
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