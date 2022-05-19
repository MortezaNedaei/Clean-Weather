package com.mooncascade.common.extensions.contexts

import android.view.View

/**
 * this file used to handle some view extensions using contexts receivers in the [View] lifecycle
 */


context(View)
val Float.dp
    get() = this * resources.displayMetrics.density

context(View)
val Int.dp
    get() = this.toFloat().dp

