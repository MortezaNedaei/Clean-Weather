package com.mooncascade.presentation.ui.home.forecasts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mooncascade.R
import com.mooncascade.data.di.DateTimeFormatter
import com.mooncascade.data.entity.forecast.ForecastEntity
import com.mooncascade.databinding.ItemNextDayForecastBinding
import com.mooncascade.domain.model.WeatherType
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class NextDaysForecastsAdapter @Inject constructor(
    private val dateFormatter: DateTimeFormatter
) : ListAdapter<ForecastEntity, NextDaysForecastsAdapter.ViewHolder>(ForecastDiffCallback()) {

    var onClickListener: ((ForecastEntity) -> Unit)? = null


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNextDayForecastBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder internal constructor(private val binding: ItemNextDayForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ForecastEntity) = item.run {

            binding.tvName.text =
                dateFormatter.parseDateToWeekdayFormat(date!!)

            // select weather icons for the next days based on current time.
            // If the current time is night, you will also see the night weather icon
            val isDay = dateFormatter.isDay()
            val weatherIcon: Int =
                if (isDay) getWeatherIconByPhenomenon(day?.phenomenon!!) else getWeatherIconByPhenomenon(
                    night?.phenomenon!!
                )
            binding.imgWeatherStatus.setImageResource(weatherIcon)


            binding.tvMinTemp.text =
                binding.root.context.getString(
                    R.string.format_min_temp,
                    night?.tempmin?.toString(),
                )

            binding.tvMaxTemp.text =
                binding.root.context.getString(
                    R.string.format_max_temp,
                    day?.tempmax?.toString(),
                )

            binding.crdItem.setOnClickListener {
                onClickListener?.invoke(this)
            }
        }

        private fun getWeatherIconByPhenomenon(phenomenon: String?): Int {
            val weatherIcon = WeatherType.values().find { weatherType ->
                weatherType.weatherDescription.contains(phenomenon!!)
            }?.weatherIcon

            return weatherIcon ?: WeatherType.UNKNOWN.weatherIcon
        }

    }

}

class ForecastDiffCallback @Inject constructor() : DiffUtil.ItemCallback<ForecastEntity>() {

    override fun areItemsTheSame(
        oldItem: ForecastEntity,
        newItem: ForecastEntity
    ): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(
        oldItem: ForecastEntity,
        newItem: ForecastEntity
    ): Boolean {
        return oldItem == newItem
    }
}
