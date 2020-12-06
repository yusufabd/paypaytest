package me.stayplus.paypaytest.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import me.stayplus.paypaytest.databinding.ActivityMainBinding
import me.stayplus.paypaytest.domain.interactor.MainInteractor
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.currencyList.observe(this) {
            //todo display currencies
            Toast.makeText(this@MainActivity, "Currency list", Toast.LENGTH_LONG).show()
        }
        viewModel.showProgress.observe(this) { show ->
            binding.progressBar.isVisible = show
        }
        viewModel.getCurrencyQuotesError.observe(this) {
            //todo display error
            Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
        }
    }
}