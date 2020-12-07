package me.stayplus.paypaytest.presentation.main

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import me.stayplus.paypaytest.R
import me.stayplus.paypaytest.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.amountInput.addTextChangedListener {
            viewModel.onAmountEntered(it?.toString())
        }
        binding.quotesRecycler.layoutManager = LinearLayoutManager(this)
        binding.quotesRecycler.adapter = MainAdapter()

        observe()
    }

    private fun observe() {
        viewModel.currencyList.observe(this) { list ->
            binding.currencySpinner.adapter = ArrayAdapter(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                list.map { it.title }
            )
            binding.currencySpinner.onItemSelectedListener = this
        }
        viewModel.currentQuotes.observe(this) {
            (binding.quotesRecycler.adapter as MainAdapter).setData(it)
        }
        viewModel.showProgress.observe(this) { show ->
            binding.progressBar.isVisible = show
        }
        viewModel.getCurrencyQuotesError.observe(this) {
            Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.onCurrencySelected(position)
        Toast.makeText(this@MainActivity, "$position picked", Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}