package com.mooncascade.data.mapper

import com.mooncascade.data.entity.forecast.PlaceEntity
import com.mooncascade.data.mapper.base.DataMapper
import com.mooncascade.domain.model.forecast.Place
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceMapper @Inject constructor() : DataMapper<Place, PlaceEntity>() {
    override fun transform(entity: PlaceEntity?): Place? {
        entity?.let {
            Place(
                id = it.id,
                name = it.name,
                phenomenon = it.phenomenon,
                tempmax = it.tempmax,
                tempmin = it.tempmin,
            )
        }
        return null
    }


}