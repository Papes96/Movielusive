package com.example.movielusive

import android.app.Application
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

object Utils: Application() {
    fun setPicture(view: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrBlank()) {
            Picasso.with(view.context)
                .load(imageUrl)
                .fit()
                .into(view)
        }
    }

    fun setPictureWithTarget(view: ImageView, imageUrl: String?, target: Target) {
        if (!imageUrl.isNullOrBlank()) {
            Picasso.with(view.context)
                .load(imageUrl)
                .into(target)
        }
    }

    fun displayText(view: TextView, text: String?) {
        view.visibility = if (text.isNullOrBlank()) View.GONE else View.VISIBLE
        view.text = text
    }

    fun displayView(view: View, data: Any?, set: () -> Unit) {
        data?.let {
            view.visibility = View.VISIBLE
            set()
        } ?: run { view.visibility = View.GONE }
    }
}