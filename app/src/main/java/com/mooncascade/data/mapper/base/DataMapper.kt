package com.mooncascade.data.mapper.base

abstract class DataMapper<DomainModel, Entity> {

    abstract fun toDomain(entity: Entity?): DomainModel?

    fun toDomain(entities: List<Entity>): List<DomainModel> {
        val list = ArrayList<DomainModel>()
        var model: DomainModel?
        for (entity in entities) {
            model = toDomain(entity)
            model?.let { list.add(it) }
        }
        return list
    }

    abstract fun toEntity(model: DomainModel?): Entity?

    fun toEntity(models: List<DomainModel>): List<Entity> {
        val list = ArrayList<Entity>()
        var entity: Entity?
        for (model in models) {
            entity = toEntity(model)
            entity?.let { list.add(it) }
        }
        return list
    }
}