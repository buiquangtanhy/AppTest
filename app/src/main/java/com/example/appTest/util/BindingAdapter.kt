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

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}
@BindingAdapter("imageUrlCoil")
fun bindingImageUrlCoil(imgView: ImageView, imgUrl: String){
    imgView.load(imgUrl)
}

//fun bindImagePicasso(imgView: ImageView, imgUrl: String?){
//    Picasso.get()
//        .load(imgUrl)
//        .placeholder(R.drawable.placeholder)
//        .error(R.drawable.error)
//        .fit()
//        .into(imageView)
//}