package com.gianghv.libads

import android.app.Activity
import android.content.Context
import com.gianghv.libads.utils.DialogLoadingAds
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class InterstitialSingleReqAdManager constructor(
    private val context: Context,
    private val mIdAdsFull01: String,
    private val mIdAdsFull02: String,
    private val mIdAdsFull03: String
) {
    private var mInterstitialAd: InterstitialAd? = null

    fun showAds(
        activity: Activity,
        onAdClose: (() -> Unit)? = null,
        onAdLoadFail: (() -> Unit)? = null
    ) {
        val requestConfiguration = RequestConfiguration.Builder().build()
        MobileAds.setRequestConfiguration(requestConfiguration)
        loadAds(onAdLoader = {
            showAds(activity, object : OnShowInterstitialCallBack {
                override fun onAdsClose() {
                    onAdClose?.invoke()
                }
            })
        }, onAdLoadFail)
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

    fun showAds(activity: Activity, listener: OnShowInterstitialCallBack) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    listener.onAdsClose()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                }

                override fun onAdShowedFullScreenContent() {
                    mInterstitialAd = null
                }

            }
            mInterstitialAd?.show(activity)
        } else {
            listener.onAdsClose()
        }
    }

    interface OnShowInterstitialCallBack {
        fun onAdsClose()
    }
}