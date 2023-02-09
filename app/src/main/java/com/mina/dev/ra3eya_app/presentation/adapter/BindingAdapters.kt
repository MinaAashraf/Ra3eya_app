package com.mina.dev.ra3eya_app.presentation.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import kotlin.math.floor

@BindingAdapter("floorNum")
fun bindFloor(txtView: TextView,floorNum:Int) {
   txtView.text = "طابق $floorNum"
}