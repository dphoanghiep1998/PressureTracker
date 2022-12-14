package com.gianghv.libads

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class InterstitialPreloadAdManager constructor(
    private val context: Context,
    private val mIdAdsFull01: String,
    private val mIdAdsFull02: String,
    private val mIdAdsFull03: String
) {
    private var mInterstitialAd: InterstitialAd? = null

    fun initAds() {
        val requestConfiguration = RequestConfiguration.Builder().build()
        MobileAds.setRequestConfiguration(requestConfiguration)
        loadAds()
    }


    fun loadAds(
        onAdLoader: (() -> Unit)? = null,
        onAdLoadFail: (() -> Unit)? = null
    ) {
        requestAdsPrepare(mIdAdsFull01, onAdLoader, onAdLoadFail = {
            requestAdsPrepare(mIdAdsFull02, onAdLoader, onAdLoadFail = {
                requestAdsPrepare(mIdAdsFull03, onAdLoader, onAdLoadFail = {
                    onAdLoadFail?.invoke()
                })
            })
        })
    }

    private fun requestAdsPrepare(
        idAds: String, onAdLoader: (() -> Unit)? = null,
        onAdLoadFail: (() -> Unit)? = null
    ) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, idAds, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
                onAdLoadFail?.invoke()
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                onAdLoader?.invoke()
            }
        })
    }

    fun showAds(activity: Activity, callBack: InterstitialAdListener?) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    callBack?.onClose()
                    loadAds()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    callBack?.onError()
                }

                override fun onAdShowedFullScreenContent() {
                    mInterstitialAd = null
                }

            }
            mInterstitialAd?.show(activity) ?: callBack?.onError()
        } else {
            callBack?.onError()
            loadAds()
        }
    }

    interface InterstitialAdListener {
        fun onClose()
        fun onError()
    }
}