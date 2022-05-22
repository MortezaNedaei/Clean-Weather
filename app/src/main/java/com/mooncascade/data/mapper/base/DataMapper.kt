package com.mooncascade.data.mapper.base

abstract class DataMapper<DomainModel, Entity> {

    abstract fun transform(entity: Entity?): DomainModel?

    fun transform(collection: Collection<Entity>): List<DomainModel> {
        val list = ArrayList<DomainModel>()
        var model: DomainModel?
        for (entity in collection) {
            model = transform(entity)
            if (model != null) {
                list.add(model)
            }
        }
        return list
    }
}