package com.mooncascade.common.extensions

import android.view.View
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar

fun View.snack(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}