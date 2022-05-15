package com.mooncascade.common.extensions.contexts

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * this file used to handle some [StateFlow] extensions using contexts receivers in the [Fragment] lifecycle
 * ```
 * MutableStateFlow(10).observe { println(it) }
 * ```
 */

context(Fragment)
fun <T> StateFlow<T>.observe(block: (value: T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect { state ->
                block(state)
            }
        }
    }
}