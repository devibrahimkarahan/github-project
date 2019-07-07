package com.example.github_project.utility

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideUtils {

    companion object {
        fun imageLoad(context: Context, url: String, view: ImageView) {
            Glide
                .with(context)
                .load(url)
                .into(view)
        }
    }

}