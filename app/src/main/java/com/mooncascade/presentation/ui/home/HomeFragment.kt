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
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.transition.Hold
import com.mooncascade.R
import com.mooncascade.common.extensions.contexts.navigateTo
import com.mooncascade.common.extensions.contexts.observe
import com.mooncascade.common.extensions.convertNumberToWords
import com.mooncascade.common.extensions.gone
import com.mooncascade.common.extensions.snack
import com.mooncascade.common.extensions.visible
import com.mooncascade.databinding.FragmentHomeBinding
import com.mooncascade.di.qualifier.MainDispatcher
import com.mooncascade.domain.model.current.Observation
import com.mooncascade.presentation.base.BaseFragment
import com.mooncascade.presentation.ui.home.forecasts.NextDaysForecastsAdapter
import com.mooncascade.presentation.ui.home.places.PlacesAdapter
import com.mooncascade.presentation.ui.home.state.ForecastsState
import com.mooncascade.presentation.ui.home.state.HomeEffect
import com.mooncascade.presentation.ui.home.state.HomeEvent
import com.mooncascade.presentation.ui.home.state.ObservationsState
import com.mooncascade.presentation.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This screen, used to show current weather and forecast for next days and nearby places
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeEvent>() {

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
            subscribeUi()
        }

    }

    private fun subscribeUi() = viewModel.viewState.observe { uiState ->
        with(uiState) {
            setObservationsUiState(observationsState)
            setForecastsUiState(forecastsState)
            error?.let {
                snack(it)
            }
        }

    }

    private fun setForecastsUiState(forecastsState: ForecastsState) = forecastsState.let {
        nextDaysForecastsAdapter.submitList(it.forecasts)
        updateProgressBar(
            it.isLoading,
            binding.includeNextDaysForecasts.includeLoading.progressBar
        )
    }


    private fun setObservationsUiState(observationsState: ObservationsState) =
        observationsState.let { uiState ->
            placesAdapter.submitList(uiState.observations)
            updateProgressBar(
                uiState.isLoading,
                binding.includeCurrentWeather.animationView,
                binding.includeNearbyPlaces.includeLoading.progressBar
            )

            //  which city (observation) should be shown as current? maybe its better to
            //  show current location weather using Google Maps Location.
            //  But we are showing a specific location for now
            uiState.observations?.find { it.wmocode == Constants.LOCATION.CODE_TARTU }
                ?.let {
                    initCurrentWeatherView(it)
                }
        }

    private fun initView() {

        initNextDaysForecastsView()

        initNearbyPlacesView()

        initClicks()
    }


    private fun initCurrentWeatherView(data: Observation) = data.run {

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
            onMapClickListener = { observation ->
                lifecycleScope.launch {
                    viewModel.onEffect(HomeEffect.NavigateToMap(observation))
                }
            }
            onClickListener = { place, cardView ->
                lifecycleScope.launch(coroutineDispatcher) {
                    viewModel.onEffect(HomeEffect.OnPlaceItemPress(place, cardView))
                }
            }
        }
    }

    private fun initClicks() {
        binding.fabDate.setOnClickListener {
            lifecycleScope.launch(coroutineDispatcher) {
                viewModel.onEffect(HomeEffect.OnFabPress)
            }
        }

        binding.tvPlacesViewMore.setOnClickListener {
            lifecycleScope.launch(coroutineDispatcher) {
                viewModel.onEffect(HomeEffect.OnViewMorePlacesPress)
            }
        }
    }

    private fun updateProgressBar(isLoading: Boolean, vararg loadingView: LottieAnimationView) {
        if (isLoading) {
            loadingView.forEach {
                it.visible()
                it.playAnimation()
            }
        } else {
            loadingView.forEach {
                it.cancelAnimation()
                it.gone()
            }
        }
    }

    override fun observeViewEffects() {
        lifecycleScope.launch(coroutineDispatcher) {
            viewModel.effectChannel.collect {
                when (it) {
                    is HomeEffect.OnFabPress -> snack("will be implemented soon")
                    is HomeEffect.OnViewMorePlacesPress -> snack("will be implemented soon")
                    is HomeEffect.NavigateToMap -> {
                        // Open Google Map
                        startActivity(Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(
                                "geo:${it.observation.latitude},${it.observation.longitude}?q=${it.observation.name}"
                            )
                        })
                    }
                    is HomeEffect.OnPlaceItemPress -> {
                        Hold().apply {
                            duration = resources.getInteger(R.integer.anim_duration_large).toLong()
                        }
                        exitTransition = Hold().apply {
                            duration = resources.getInteger(R.integer.anim_duration_large).toLong()
                        }

                        reenterTransition = Hold().apply {
                            duration = resources.getInteger(R.integer.anim_duration_large).toLong()
                        }

                        val extras =
                            FragmentNavigatorExtras(it.cardView to it.cardView.transitionName)
                        HomeFragmentDirections.actionHomeFragmentToPlaceDetailsFragment(
                            it.observation,
                            it.cardView.transitionName
                        )
                            .also { directions -> navigateTo(directions, extras) }
                    }
                    is HomeEffect.ShowError -> snack(it.message)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}