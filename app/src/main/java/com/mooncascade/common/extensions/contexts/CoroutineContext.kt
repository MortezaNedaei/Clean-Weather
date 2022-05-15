package com.mooncascade.common.extensions.contexts

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * this file used to handle some coroutine extensions using contexts receivers in the [CoroutineScope] lifecycle
 * ```
 * CoroutineScope(Job()).launch {
 *      MutableStateFlow(10).launchFlow { println(it) }
 * }
 * ```
 */


context(CoroutineScope)
fun <T> Flow<T>.launchFlow(block: (value: T) -> Unit) = launch {
    collect { state ->
        block(state)
    }
}

