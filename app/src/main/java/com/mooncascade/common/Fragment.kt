package com.mooncascade.common

import android.graphics.Color
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialContainerTransform.FADE_MODE_THROUGH
import com.mooncascade.R

/**
 * assisted injection requires the use of a factory annotated with [dagger.assisted.AssistedFactory]
 * This factory should be provided as a factoryProducer to the [androidx.fragment.app.viewModels]
 * extension function with some implementations using factory method existed in [AbstractSavedStateViewModelFactory]
 * This extension function will create this with the provided [SavedStateHandle] using lazy delegate
 */
@Suppress("UNCHECKED_CAST")
@MainThread
inline fun <reified T : ViewModel> Fragment.assistedViewModel(
    crossinline viewModelProducer: (SavedStateHandle) -> T
) = viewModels<T> {
    object : AbstractSavedStateViewModelFactory(this, arguments) {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ) = viewModelProducer(handle) as T
    }
}

/**
 * Used to apply a MaterialContainerTransform shared element to a Fragment
 */
fun Fragment.materialContainerTransform() {
    sharedElementEnterTransition = MaterialContainerTransform().apply {
        drawingViewId = R.id.nav_host_fragment_content_main
        duration = resources.getInteger(R.integer.anim_duration_large).toLong()
        startContainerColor = Color.TRANSPARENT
        endContainerColor = Color.TRANSPARENT
        scrimColor = Color.TRANSPARENT
        fadeMode = FADE_MODE_THROUGH
        fadeProgressThresholds = MaterialContainerTransform.ProgressThresholds(0f, 1f)
        setAllContainerColors(resources.getColor(android.R.color.transparent))
    }
}