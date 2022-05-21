package com.mooncascade.common.extensions

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.snack(message: String, duration: Int = Snackbar.LENGTH_SHORT) =
    requireView().snack(message, duration)