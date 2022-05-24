package com.mooncascade.presentation.ui.home.places

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.mooncascade.R
import com.mooncascade.databinding.ItemPlaceBinding
import com.mooncascade.domain.model.current.Observation
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class PlacesAdapter @Inject constructor(
) : ListAdapter<Observation, PlacesAdapter.ViewHolder>(PlaceDiffCallback()) {

    /**
     * navigates to the selected place with the given observation and the shared element transition id
     */
    var onClickListener: ((Observation, MaterialCardView) -> Unit)? = null

    /**
     * opens the GoogleMap app with the given observation
     */
    var onMapClickListener: ((Observation) -> Unit)? = null


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaceBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder internal constructor(private val binding: ItemPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Observation) = item.run {
            binding.crdItem.transitionName = name
            binding.tvName.text = name
            binding.tvTemp.text =
                binding.root.resources.getString(R.string.format_air_temperature, airtemperature)
            binding.tvHumidity.text =
                binding.root.resources.getString(R.string.format_humidity, relativehumidity)
            binding.tvVisibility.text =
                binding.root.resources.getString(
                    R.string.format_visibility,
                    if (visibility.isNullOrEmpty()) "0" else visibility.toString()
                )

            binding.imgLocation.setOnClickListener {
                onMapClickListener?.invoke(this)
            }
            binding.crdItem.setOnClickListener {
                onClickListener?.invoke(this, binding.crdItem)
            }
        }

    }

}

class PlaceDiffCallback @Inject constructor() : DiffUtil.ItemCallback<Observation>() {

    override fun areItemsTheSame(
        oldItem: Observation,
        newItem: Observation
    ): Boolean {
        return oldItem.wmocode == newItem.wmocode
    }

    override fun areContentsTheSame(
        oldItem: Observation,
        newItem: Observation
    ): Boolean {
        return oldItem == newItem
    }
}
