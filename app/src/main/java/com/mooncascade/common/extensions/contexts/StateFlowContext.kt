package com.mooncascade.common.extensions.contexts

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * this file used to handle some [StateFlow] extensions using contexts receivers in the [Fragment] lifecycle
 */

/**
 * this method can observe a single [StateFlow]
 * ```
 * MutableStateFlow(10).observe { println(it) }
 * ```
 */
context(Fragment)
fun <T> StateFlow<T>.observe(block: (value: T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect { state ->
                block(state)
            }
        }
    }
}

/**
 * this method can observe variable arguments of [StateFlow]s with the same data type.
 * Might be used for the cases like stateIn flattenMerged flowOf something
 *
 * ```
 * MutableStateFlow(10).observe { println(it) }
 * ```
 *
 *  ```
 * lifecycleScope.launch {
 *  flowOf(
 *      MutableStateFlow(arrayOf(10, 20)),
 *      MutableStateFlow(arrayOf(30, 40))
 *  ).flattenMerge()
 *  .stateIn(this)
 *  .observe(
 *      { println(it) },
 *      { println(it) }
 *   )
 * }
 * ```
 */
context(Fragment)
fun <T> StateFlow<T>.observe(vararg block: (value: T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            block.forEach { flow ->
                launch {
                    collect { flow(it) }
                }
            }
        }
    }
}