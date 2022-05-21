package com.mooncascade.presentation.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.android.material.transition.Hold
import com.mooncascade.R
import com.mooncascade.common.extensions.contexts.navigateTo
import com.mooncascade.common.extensions.contexts.observe
import com.mooncascade.common.extensions.convertNumberToWords
import com.mooncascade.common.extensions.gone
import com.mooncascade.common.extensions.snack
import com.mooncascade.common.extensions.visible
import com.mooncascade.data.di.qualifier.MainDispatcher
import com.mooncascade.data.entity.current.ObservationEntity
import com.mooncascade.databinding.FragmentHomeBinding
import com.mooncascade.domain.model.ViewState
import com.mooncascade.presentation.base.BaseFragment
import com.mooncascade.presentation.ui.home.forecasts.NextDaysForecastsAdapter
import com.mooncascade.presentation.ui.home.places.PlacesAdapter
import com.mooncascade.presentation.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This screen, used to show current weather and forecast for next days and nearby places
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var placesAdapter: PlacesAdapter

    @Inject
    lateinit var nextDaysForecastsAdapter: NextDaysForecastsAdapter

    @Inject
    @MainDispatcher
    lateinit var coroutineDispatcher: CoroutineDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Hold()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        lifecycleScope.launch(coroutineDispatcher) {
            initView()
            initData()
        }

    }

    private fun initView() {

        initNextDaysForecastsView()

        initNearbyPlacesView()

        initClicks()
    }

    private fun initData() {

        initCurrentWeatherData()

        initNextDaysForecastsData()
    }

    private fun initCurrentWeatherData() = viewModel.currentWeatherFlow.observe { currentWeather ->
        when (currentWeather.status) {
            ViewState.Status.SUCCESS -> {
                binding.includeCurrentWeather.includeLoading.progressBar.gone()
                binding.includeNearbyPlaces.includeLoading.progressBar.gone()
                placesAdapter.submitList(currentWeather.data?.observations)

                // TODO: which city (observation) should be shown as current? maybe its better to
                //  show current location weather using Google Maps Location.
                //  But we are showing a specific location for now
                currentWeather.data?.observations
                    ?.find { it.wmocode == Constants.LOCATION.CODE_TARTU }
                    ?.let {
                        initCurrentWeatherView(it)
                    }

            }
            ViewState.Status.ERROR -> {
                binding.includeCurrentWeather.includeLoading.progressBar.gone()
                binding.includeNearbyPlaces.includeLoading.progressBar.gone()
                snack(currentWeather.message ?: "")
            }
            ViewState.Status.LOADING -> {
                binding.includeCurrentWeather.includeLoading.progressBar.visible()
                binding.includeNearbyPlaces.includeLoading.progressBar.visible()
            }
            else -> {}
        }
    }

    private fun initCurrentWeatherView(data: ObservationEntity) = data.run {

        binding.includeCurrentWeather.apply {
            tvCityName.text = name
            tvWeatherStatus.text = phenomenon
            tvWindSpeed.text =
                getString(R.string.format_wind_speed, windspeed)
            tvAirPressure.text =
                getString(R.string.format_air_pressure, airpressure)
            if (airtemperature.isNullOrEmpty()) {
                tvTemp.text = "- -"
            } else {
                tvTemp.text =
                    getString(R.string.format_temp, airtemperature)
                crdCurrentWeather.setOnClickListener {
                    snack(
                        // Show current weather in alphabet words format
                        getString(
                            R.string.format_temp_degrees,
                            airtemperature.toFloat().toInt().convertNumberToWords()
                        )
                    )
                }

            }

            animationView.setAnimation(
                viewModel.getCurrentWeatherAnimation(phenomenon)
            )
        }
    }


    private fun initNextDaysForecastsData() = viewModel.nextDaysForecastsFlow.observe { forecasts ->
        when (forecasts.status) {
            ViewState.Status.SUCCESS -> {
                binding.includeNextDaysForecasts.includeLoading.progressBar.gone()
                nextDaysForecastsAdapter.submitList(forecasts.data)
            }
            ViewState.Status.ERROR -> {
                binding.includeNextDaysForecasts.includeLoading.progressBar.gone()
                snack(forecasts.message ?: "")
            }
            ViewState.Status.LOADING -> {
                binding.includeNextDaysForecasts.includeLoading.progressBar.visible()
            }
            else -> {}
        }
    }


    private fun initNextDaysForecastsView() {
        binding.includeNextDaysForecasts.rvNextDaysForecasts.adapter =
            nextDaysForecastsAdapter.apply {
                onClickListener = { forecast ->
                    snack("Weather Description: ${forecast.day?.text}")
                }
            }
    }

    private fun initNearbyPlacesView() {
        binding.includeNearbyPlaces.rvPlaces.adapter = placesAdapter.apply {
            onMapClickListener = { place ->
                // Open Google Map
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(
                        "geo:${place.latitude},${place.longitude}?q=${place.name}"
                    )
                })
            }
            onClickListener = { place, cardView ->
                Hold().apply {
                    duration = resources.getInteger(R.integer.anim_duration_large).toLong()
                }
                exitTransition = Hold().apply {
                    duration = resources.getInteger(R.integer.anim_duration_large).toLong()
                }

                reenterTransition = Hold().apply {
                    duration = resources.getInteger(R.integer.anim_duration_large).toLong()
                }

                val extras = FragmentNavigatorExtras(cardView to cardView.transitionName)
                HomeFragmentDirections.actionHomeFragmentToPlaceDetailsFragment(
                    place,
                    cardView.transitionName
                )
                    .also { directions -> navigateTo(directions, extras) }
            }
        }
    }

    private fun initClicks() {
        binding.fabDate.setOnClickListener {
            // TODO: create additional dialog fragment to show date picker
            snack("will be implemented soon")
        }

        binding.tvPlacesViewMore.setOnClickListener {
            // TODO: create additional fragment to show list of nearby places in vertical list
            snack("will be implemented soon")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}