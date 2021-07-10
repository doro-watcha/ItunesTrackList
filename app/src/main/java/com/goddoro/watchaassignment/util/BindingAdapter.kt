package com.goddoro.watchaassignment.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

@BindingAdapter("srcUrl", "placeholder", requireAll = false)
fun ImageView.loadUrlAsync(url: String?, placeholder: Drawable? = null) {
    if (url == null) {
        Glide.with(this).load(placeholder).diskCacheStrategy(DiskCacheStrategy.ALL).into(this)
    } else {
        Glide.with(this).load(url)
            .apply {
                if (placeholder != null)
                    (placeholder)
            }
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }
}
@BindingAdapter("android:visibility")
fun View.setVisibility (isVisible : Boolean) {
    if ( isVisible) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

