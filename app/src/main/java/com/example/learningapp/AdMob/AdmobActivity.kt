package com.example.learningapp.AdMob

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivityAdmobBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class AdmobActivity : AppCompatActivity() {
    private lateinit var binding :ActivityAdmobBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAdmobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        AdUtil.initialize(this)
        AdUtil.loadBannerAd(binding.adView)
        AdUtil.loadInterstitialAd(this, "ca-app-pub-3940256099942544/1033173712")
        binding.btShowAd.setOnClickListener{
       AdUtil.showInterstitialAd(this)
     }
    }
}