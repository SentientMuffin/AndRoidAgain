package com.example.androidagain

import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidagain.coresystem.CoreSystem
import com.example.androidagain.coresystem.GridField
import com.example.androidagain.databinding.CoreGameBinding
import kotlin.math.min

class CoreGameActivity : AppCompatActivity() {

    private lateinit var binding: CoreGameBinding

    private lateinit var renderMisc: ImageView
    private lateinit var renderField: ImageView
    private lateinit var renderRng: ImageView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CoreGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemUI()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        renderPlayingField()
    }

    private fun renderPlayingField() {
        renderField = binding.renderField
        renderRng = binding.renderRng

        val coreSystem = CoreSystem(
            3, 4,
            renderField.width, renderField.height, renderRng.width, renderRng.height
        )
        for (colorUnit in coreSystem.retrievable) {
            coreSystem.rngField.applyLayeredColorUnit(colorUnit!!)
        }

        // set bitmap as background to ImageView
        renderField.background = BitmapDrawable(resources, coreSystem.mainField.bitmap)
        renderRng.background = BitmapDrawable(resources, coreSystem.rngField.bitmap)
    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }
    }

}