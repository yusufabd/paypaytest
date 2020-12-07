package me.stayplus.paypaytest.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.stayplus.paypaytest.databinding.ItemQuoteBinding
import me.stayplus.paypaytest.domain.entities.Quote
import me.stayplus.paypaytest.extensions.formatDecimal

class MainAdapter : RecyclerView.Adapter<MainAdapter.QuoteHolder>(){

    private val list = ArrayList<Quote>()

    fun setData(newList: List<Quote>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteHolder {
        val itemBinding = ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuoteHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: QuoteHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class QuoteHolder(private val binding: ItemQuoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quote: Quote) {
            val from = "${quote.amount.formatDecimal()} ${quote.currencyFrom}"
            val to = "${quote.calculateQuote().formatDecimal()} ${quote.currencyTo}"
            val rate = "1 ${quote.currencyFrom} = ${String.format("%2f", quote.rate)} ${quote.currencyTo}"

            binding.from.text = from
            binding.to.text = to
            binding.rate.text = rate
        }
    }

}