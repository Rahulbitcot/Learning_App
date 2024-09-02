package com.example.learningapp.balloonLibrary

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivityBalloonBinding
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class BalloonActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBalloonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBalloonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        showBalloonSequentially()

        binding.home.setOnClickListener {
            showBalloon(it, "Home")
        }

        binding.call.setOnClickListener{
            showBalloon(it, "Call")
        }
        binding.chat.setOnClickListener{
            showBalloon(it, "Chat")
        }
    }

    private  fun showBalloonSequentially() {
        try {
            val balloon1 = createBalloon("Home")
            val balloon2 = createBalloon("Chat")
            val balloon3 = createBalloon("Call")

             lifecycleScope.launch {  showBalloonWithDelay(balloon1, binding.home)
                 showBalloonWithDelay(balloon2, binding.call)
                 showBalloonWithDelay(balloon3, binding.chat) }

        } catch (e: Exception) {
            Log.e("BalloonLibrary", "Error showing balloons sequentially: ${e.message}", e)
        }
    }

    private fun showBalloon(anchor : View, text: String) {
        val balloon = createBalloon(text)
        balloon.showAlignTop(anchor)
    }

    private suspend fun showBalloonWithDelay(balloon: Balloon, anchor: View) {
        balloon.showAlignTop(anchor)
        balloon.dismissWithDelay(2000L)
        balloon.awaitDismiss()
    }
    private suspend fun Balloon.awaitDismiss() = suspendCancellableCoroutine<Unit> { cont ->
        setOnBalloonDismissListener {
            if (cont.isActive) {
                cont.resume(Unit)
            }
        }
    }

    private fun createBalloon(text: String): Balloon {
        return Balloon.Builder(this)
            .setWidthRatio(0.8f) // Set width to 80% of the screen width
            .setHeight(BalloonSizeSpec.WRAP) // Wrap content for height
            .setText(text) // Set the balloon text
            .setTextColorResource(R.color.white) // Set text color
            .setTextSize(15f) // Set text size
            .setTextTypeface(Typeface.BOLD) // Set text style
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR) // Align arrow to anchor
            .setArrowSize(10) // Set arrow size
            .setArrowPosition(0.5f) // Center the arrow
            .setPadding(12) // Set padding
            .setCornerRadius(8f) // Set corner radius
            .setBackgroundColorResource(R.color.black) // Set background color
            .setBalloonAnimation(BalloonAnimation.ELASTIC) // Set animation
            .setIsVisibleOverlay(true) // Show overlay
            .setOverlayColorResource(R.color.light_yellow) // Overlay color
            .setLifecycleOwner(this)
            .setAutoDismissDuration(2000L)
            .setDismissWhenTouchOutside(false)
            .setOnBalloonOverlayClickListener {
            }
            .build()
    }
}