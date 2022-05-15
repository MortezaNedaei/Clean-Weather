package com.mooncascade.common.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snack(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}