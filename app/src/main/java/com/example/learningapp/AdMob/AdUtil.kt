package com.example.learningapp.AdMob

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object AdUtil {
    private var interstitialAd: InterstitialAd? = null
    private var adIsLoading: Boolean = false

    fun initialize(context: Context) {
        MobileAds.initialize(context)
    }

    fun loadBannerAd(adView: AdView){
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
    fun loadInterstitialAd(context: Context, adUnitId: String) {
        if (adIsLoading) return

        adIsLoading = true
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, adUnitId, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                adIsLoading = false
                interstitialAd = null
            }

            override fun onAdLoaded(ad: InterstitialAd) {
                interstitialAd = ad
                adIsLoading = false
            }
        })
    }

    fun showInterstitialAd(context: Context) {
        interstitialAd?.let {
            it.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d("AdMobUtil", "Interstitial Ad Dismissed")
                    loadInterstitialAd(context, it.adUnitId)
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.e("AdMobUtil", "Interstitial Ad Failed to Show: ${adError.message}")
                    loadInterstitialAd(context, it.adUnitId)
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d("AdMobUtil", "Interstitial Ad Showed")
                    interstitialAd = null
                }
            }
            it.show(context as AppCompatActivity)
        } ?: run {
            Log.d("AdMobUtil", "Interstitial Ad not ready yet")
        }
    }
}