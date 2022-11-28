package com.example.movielusive.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import com.example.data.domain.model.Collection
import com.example.movielusive.R
import com.example.movielusive.Utils
import com.example.movielusive.databinding.ViewImageCardBinding
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class ImageCardView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private val binding: ViewImageCardBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ViewImageCardBinding.inflate(inflater, this, true)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ImageCardView)
        val text = attributes.getString(R.styleable.ImageCardView_text)
        if (text != null) binding.tvTitle.text = text
    }

    fun setCard(collection: Collection?){
        collection?.let{
        Utils.setPictureWithTarget(binding.ivImage, collection.backdrop ?: collection.poster, PicassoTarget(binding.ivImage))
        binding.tvTitle.text = collection.name
        }
    }

    fun getTextView(): TextView {
        return binding.tvTitle
    }

    fun getImageView(): ShapeableImageView {
        return binding.ivImage
    }

    inner class PicassoTarget(private val view: ImageView): Target {
        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            val swatch = bitmap?.let { Palette.Builder(it).generate() }
            val colorGradient = swatch?.dominantSwatch?.rgb ?: Color.TRANSPARENT
            val colorFont = swatch?.dominantSwatch?.rgb ?: Color.WHITE
            val gradient = intArrayOf(
                ColorUtils.setAlphaComponent(colorGradient, 75),
                ColorUtils.blendARGB(ColorUtils.setAlphaComponent(colorGradient, 125), Color.BLACK, 0.5f))

            view.setImageBitmap(bitmap)
            binding.tvTitle.setTextColor(ColorUtils.blendARGB(colorFont, Color.WHITE, 0.9f))
            binding.tvTitle.background = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, gradient).apply {
                gradientType = GradientDrawable.RADIAL_GRADIENT
                gradientRadius = 650.0f
            }
            binding.cardView.visibility = View.VISIBLE
        }

        override fun onBitmapFailed(errorDrawable: Drawable?) {}

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

    }
}