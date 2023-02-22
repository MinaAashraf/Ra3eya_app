package com.mina.dev.ra3eya_app.presentation.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mina.dev.ra3eya_app.presentation.utils.hide
import com.mina.dev.ra3eya_app.presentation.utils.hideUsingGone
import com.squareup.picasso.Picasso
import kotlin.math.floor

@BindingAdapter("floorNum")
fun bindFloor(txtView: TextView, floorNum: Int) {
    txtView.text = "طابق $floorNum"
}


@BindingAdapter("img_url")
fun bindImage(imageView: ImageView, url: String?) {
    url?.let {
        Picasso.get().load(it).into(imageView)
    } ?: run {
        imageView.hideUsingGone()
    }
}