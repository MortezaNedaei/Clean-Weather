package com.mooncascade.di.qualifier


import javax.inject.Qualifier

/**
 * This class uses qualifier which is an annotation that you use to identify a specific
 * binding for a type when that type has multiple bindings defined.
 * @see [com.mooncascade.di.network.NetworkModule]
 *
 */

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class BaseUrl

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MediaBaseUrl
