package com.example.github_project.utility

import android.content.Context
import android.widget.Toast

class ToastMessages {

    companion object {
        fun showToast(context: Context, message: String, duration: Int) {
            Toast.makeText(context, message, duration).show()
        }
    }
}