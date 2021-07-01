package com.example.appTest.util

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import com.bumptech.glide.Glide
import com.example.appTest.R
import com.example.appTest.ui.AppTextApplication.Companion.appContext
import com.squareup.picasso.Picasso

@BindingAdapter("bindingImageResource")
fun bindingImageResource(view: ImageView, icon: Int) {
    view.setImageResource(icon)
}

@BindingAdapter("bindingImageUrl")
fun bindingImageUrl(view: ImageView, url: String) {
    Glide.with(appContext).load(url).into(view)
}

@BindingAdapter("imageUrlCoil")
fun bindingImageUrlCoil(imgView: ImageView, imgUrl: String) {
    imgView.load(imgUrl)
}