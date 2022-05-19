package com.mooncascade.common.extensions

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * assisted injection requires the use of a factory annotated with [dagger.assisted.AssistedFactory]
 * This factory should be provided as a factoryProducer to the [androidx.activity.viewModels]
 * extension function with some implementations using factory method existed in [AbstractSavedStateViewModelFactory]
 * This extension function will create this with the provided [SavedStateHandle] using lazy delegate
 */
@Suppress("UNCHECKED_CAST")
@MainThread
inline fun <reified T : ViewModel> ComponentActivity.assistedViewModel(
    crossinline viewModelProducer: (SavedStateHandle) -> T
) = viewModels<T> {
    object : AbstractSavedStateViewModelFactory(this, intent.extras) {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ) = viewModelProducer(handle) as T
    }
}