package com.exchange.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.exchange.R
import com.exchange.domain.model.Rate
import com.exchange.ui.ExchangeViewModel.UiState
import com.exchange.ui.ExchangeViewModel.UiState.Currencies
import com.exchange.ui.ExchangeViewModel.UiState.Error
import com.exchange.ui.ExchangeViewModel.UiState.Loading
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.exchange_fragment.currency_list
import kotlinx.android.synthetic.main.exchange_fragment.loading
import java.util.Currency
import javax.inject.Inject

class ExchangeFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ExchangeViewModel> { vmFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.exchange_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currency_list.apply {
            setHasFixedSize(true)
            adapter = CurrencyAdapter(viewModel::onBaseRateChanged)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.state.observe(this, ::render)
    }

    private fun render(state: UiState) = when (state) {
        is Loading -> {
            loading.isVisible = true
            currency_list.isVisible = false
        }
        is Currencies -> {
            loading.isVisible = false
            currency_list.isVisible = true
            currency_list.setData(state.data)
        }
        is Error -> {
            loading.isVisible = false
            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun RecyclerView.setData(data: Map<Currency, Rate>) {
        (adapter as CurrencyAdapter).setData(data)
    }

    companion object {
        fun newInstance() = ExchangeFragment()
    }
}
