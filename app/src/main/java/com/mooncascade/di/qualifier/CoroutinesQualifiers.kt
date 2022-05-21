package com.mooncascade.di.qualifier


import javax.inject.Qualifier

/**
 * This class uses qualifier which is an annotation that you use to identify a specific
 * binding for a type when that type has multiple bindings defined.
 * @see [com.mooncascade.di.CoroutinesModule]
 *
 */

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainImmediateDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ApplicationScope
