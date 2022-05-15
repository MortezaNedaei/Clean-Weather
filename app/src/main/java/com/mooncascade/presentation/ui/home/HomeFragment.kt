package com.mooncascade.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mooncascade.R
import com.mooncascade.common.extensions.contexts.navigateTo
import com.mooncascade.common.extensions.contexts.observe
import com.mooncascade.common.extensions.snack
import com.mooncascade.data.di.qualifier.MainDispatcher
import com.mooncascade.databinding.FragmentHomeBinding
import com.mooncascade.domain.model.ViewState
import com.mooncascade.presentation.base.BaseFragment
import com.mooncascade.presentation.ui.home.places.PlacesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var placesAdapter: PlacesAdapter

    @Inject
    @MainDispatcher
    lateinit var coroutineDispatcher: CoroutineDispatcher

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(coroutineDispatcher) {
            viewModel.forecastsFlow.observe { forecasts ->
                when (forecasts.status) {
                    ViewState.Status.SUCCESS -> {
                        //placesAdapter.submitList(forecasts.forecasts)
                        requireView().snack("forecasts counts: " + forecasts.data?.size)
                    }
                    ViewState.Status.ERROR -> {
                        requireView().snack(forecasts.message ?: "")
                    }
                    ViewState.Status.LOADING -> {
                        requireView().snack("Loading")
                    }
                    else -> {
                        requireView().snack(forecasts.status.name)
                    }
                }
            }
        }

        binding.fabDate.setOnClickListener {
            navigateTo(R.id.action_HomeFragment_to_PlaceDetailsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}