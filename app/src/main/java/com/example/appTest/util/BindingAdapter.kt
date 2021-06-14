package com.example.appTest.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.appTest.ui.AppTextApplication.Companion.appContext

@BindingAdapter("bindingImageResource")
fun bindingImageResource(view: ImageView, icon: Int) {
    view.setImageResource(icon)
}

@BindingAdapter("bindingImageUrl")
fun bindingImageUrl(view: ImageView, url: String) {
    Glide.with(appContext).load(url).into(view)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide.with(appContext)
            .load(imgUrl)
            .into(imgView)
    }
}