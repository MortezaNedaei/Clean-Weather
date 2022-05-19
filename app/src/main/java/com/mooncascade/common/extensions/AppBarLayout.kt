package com.mooncascade.common.extensions

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

fun AppBarLayout.onStateChangedListener(collapsed: (Boolean) -> Unit) {
    addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
        if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
            collapsed(true)
        } else if (verticalOffset == 0) {
            collapsed(false)
        } else {
            // Somewhere between collapsed and
        }
    })
}