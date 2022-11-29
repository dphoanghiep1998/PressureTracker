package com.gianghv.libads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class RewardAdsManager(
    private val context: Context,
    private val mIdAdsFull01: String
) {
    private var rewardedAd: RewardedAd? = null

    fun initAds() {
        val requestConfiguration = RequestConfiguration.Builder().build()
        MobileAds.setRequestConfiguration(requestConfiguration)
        loadAds()
    }

    private fun loadAds() {
        val fullScreenContentCallback: FullScreenContentCallback =
            object : FullScreenContentCallback() {
                override fun onAdShowedFullScreenContent() {
                    // Code to be invoked when the ad showed full screen content.
                }

                override fun onAdDismissedFullScreenContent() {
                    rewardedAd = null
                    loadAds()
                    // Code to be invoked when the ad dismissed full screen content.
                }
            }

        RewardedAd.load(
            context,
            mIdAdsFull01,
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(p0: RewardedAd) {
                    Log.e("RewardAdsLoader","onAdLoaded")
                    rewardedAd = p0
                    rewardedAd?.fullScreenContentCallback = fullScreenContentCallback
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    Log.e("RewardAdsLoader","Eror"+p0.message)
                }
            })
    }

    fun showRewardAds(activity: Activity, onShowRewardAdListener: OnShowRewardAdListener) {
        if (rewardedAd != null) {
            rewardedAd?.show(
                activity
            ) {
                onShowRewardAdListener.onShowRewardSuccess()
            }
        } else {
            Log.e("RewardAdsLoader","rewardedAd = null")
            loadAds()
            onShowRewardAdListener.onRequestAdsError()
        }
    }

    interface OnShowRewardAdListener {
        fun onShowRewardSuccess()
        fun onRequestAdsError()
    }
}