package com.example.learningapp.shimmerEffect

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivityThirdBinding
import com.facebook.shimmer.ShimmerFrameLayout

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding : ActivityThirdBinding
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

         shimmerFrameLayout = findViewById(R.id.shimmer_view_container)
         shimmerFrameLayout.startShimmer()

        Handler().postDelayed({
            shimmerFrameLayout.stopShimmer()
            binding.mainConstrain.visibility = View.VISIBLE
            shimmerFrameLayout.visibility = View.GONE
        }, 1500)
    }
}