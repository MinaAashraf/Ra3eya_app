package com.mina.dev.ra3eya_app.presentation.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar


fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.hideUsingGone (){
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

