package com.khush.blackboard

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.view.WindowInsets

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val customCanvasView = CustomCanvasView(this)
        setContentView(customCanvasView)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            customCanvasView.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
        }
    }
}