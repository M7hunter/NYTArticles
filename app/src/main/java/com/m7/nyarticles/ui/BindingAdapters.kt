package com.m7.nyarticles.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.also {
        imgView.load(imgUrl) {
            listener(
                onError = { _, errResult ->
                    errResult.throwable.printStackTrace()
                }
            )
        }
    }
}