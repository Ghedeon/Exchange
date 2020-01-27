package com.exchange.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.exchange.R
import com.exchange.domain.model.Rate
import com.exchange.ext.getFlag
import com.exchange.ui.CurrencyAdapter.ViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.currency_item.amount
import kotlinx.android.synthetic.main.currency_item.currency_code
import kotlinx.android.synthetic.main.currency_item.currency_flag
import kotlinx.android.synthetic.main.currency_item.currency_name
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import java.text.DecimalFormat
import java.util.Currency

class CurrencyAdapter(
    private val onBaseRateChanged: (rate: Rate) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private val items = mutableListOf<Rate>()

    init {
        setHasStableIds(true)
    }

    fun setData(newData: Map<Currency, Rate>) {
        if (items.isNotEmpty()) {
            items.forEachIndexed { idx, rate ->
                items[idx] = newData.getOrElse(rate.currency) { rate }
            }
        } else items.addAll(newData.map { it.value })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.currency_item, parent, false)
        return ViewHolder(view) { position, rate ->
            items.move(position, 0)
            notifyItemMoved(position, 0)
            onBaseRateChanged(rate)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long = items[position].currency.numericCode.toLong()

    class ViewHolder(
        override val containerView: View,
        private val onBaseRateChanged: (position: Int, item: Rate) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: Rate) {
            if (!amount.isFocused) amount.setText(item.amount.format())
            currency_code.text = item.currency.currencyCode
            currency_name.text = item.currency.displayName
            currency_flag.setImageResource(item.currency.getFlag(itemView.context))

            amount.doAfterTextChanged {
                if (amount.isFocused) {
                    val amount = it.toString().toBigDecimalOrNull() ?: ZERO
                    onBaseRateChanged(layoutPosition, item.copy(amount = amount))
                }
            }

            amount.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) return@setOnFocusChangeListener
                if (layoutPosition != 0) onBaseRateChanged(layoutPosition, item)
            }

            itemView.setOnClickListener {
                if (layoutPosition != 0) amount.requestFocus()
            }
        }
    }
}

private fun <T> MutableList<T>.move(from: Int, to: Int) = apply {
    val item = removeAt(from)
    add(to, item)
}

private fun BigDecimal.format(): String = DecimalFormat("00.00").format(this)
