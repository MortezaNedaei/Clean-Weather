package com.mooncascade.data.respository.datasource.base

enum class DataSourceFactoryType {
    REMOTE,
    LOCAL
}

interface BaseDataSourceFactory<T> {
    fun create(type: DataSourceFactoryType): T
}