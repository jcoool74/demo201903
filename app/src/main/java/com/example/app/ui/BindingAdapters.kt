package com.example.app.ui

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object BindingAdapters {
    @BindingAdapter("app:imageUrl")
    @JvmStatic fun imageUrl(imageView: ImageView, url: String?) {
        url ?: return
        Glide.with(imageView.context).load(url).apply(RequestOptions.circleCropTransform()).into(imageView)
    }
}