package com.mooncascade.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import coil.request.CachePolicy
import com.mooncascade.R
import com.mooncascade.common.assistedViewModel
import com.mooncascade.common.extensions.*
import com.mooncascade.common.extensions.contexts.observe
import com.mooncascade.common.materialContainerTransform
import com.mooncascade.data.di.qualifier.MainDispatcher
import com.mooncascade.data.network.WeatherApi
import com.mooncascade.databinding.FragmentPlaceDetailsBinding
import com.mooncascade.domain.model.ViewState
import com.mooncascade.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * This screen, used to show place forecast details
 */
@AndroidEntryPoint
class PlaceDetailsFragment : BaseFragment() {

    private var _binding: FragmentPlaceDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: PlaceDetailsViewModel.PlaceDetailsViewModelFactory
    private val viewModel by assistedViewModel { viewModelFactory.create(it) }

    @Inject
    @MainDispatcher
    lateinit var coroutineDispatcher: CoroutineDispatcher

    private val args by navArgs<PlaceDetailsFragmentArgs>()
    private val place by lazy(LazyThreadSafetyMode.NONE) { args.item }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        materialContainerTransform()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPlaceDetailsBinding.inflate(inflater, container, false)
        return binding.root.apply {
            // apply transition on whole view
            transitionName = args.transitionName
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        initLocationWeatherData()
    }


    private fun initLocationWeatherData() = viewModel.locationWeatherFlow.observe { forecast ->
        when (forecast.status) {
            ViewState.Status.SUCCESS -> {
                binding.includeLoading.progressBar.gone()
            }
            ViewState.Status.ERROR -> {
                binding.includeLoading.progressBar.gone()
                requireView().snack(forecast.message ?: "")
            }
            ViewState.Status.LOADING -> {
                binding.includeLoading.progressBar.visible()
            }
            else -> {}
        }
    }

    private fun initView() {

        initAppBar()

        binding.tvCityName.text = place.name.also { binding.tvCityName2.text = it }
        if (!place.airtemperature.isNullOrEmpty()) {
            binding.tvTemp2.text = getString(
                R.string.format_temp_full_degrees,
                place.airtemperature,
                place.airtemperature?.toFloat()?.toInt()?.convertNumberToWords()
            )
        }



        binding.imgCover.load(WeatherApi.Endpoints.GET_RANDOM_IMAGE) {
            crossfade(true)
            placeholder(R.drawable.ic_placeholder)
            error(R.drawable.ic_placeholder)
            diskCachePolicy(CachePolicy.ENABLED)
        }
    }

    private fun initAppBar() {
        binding.appBarLayout.onStateChangedListener { collapsed ->
            if (collapsed) {
                binding.tvCityName2.visible()
                binding.tvTemp2.visible()
            } else {
                binding.tvCityName2.gone()
                binding.tvTemp2.gone()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}