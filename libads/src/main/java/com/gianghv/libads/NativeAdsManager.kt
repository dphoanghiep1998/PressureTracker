package com.gianghv.libads

import android.content.Context
import android.util.Log
import com.gianghv.libads.utils.AppLogger
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd

class NativeAdsManager constructor(
    private val context: Context,
    private val idNativeAds01: String,
    private val idNativeAds02: String,
    private val idNativeAds03: String
) {
    private var nativeAd: NativeAd? = null
    private var mAdLoader: AdLoader? = null

    val mNativeAd get() = nativeAd

    fun loadAds(onLoadSuccess: ((NativeAd) -> Unit)? = null, onLoadFail: ((failed: Boolean) -> Unit)? = null) {
        requestAds(idNativeAds01, onLoadSuccess, onLoadFail = {
            requestAds(idNativeAds02, onLoadSuccess, onLoadFail = {
                requestAds(idNativeAds03, onLoadSuccess, onLoadFail = {
                    onLoadFail?.invoke(true)
                })
            })
        })
    }

    fun requestAds(
        idNativeAds: String,
        onLoadSuccess: ((NativeAd) -> Unit)? = null,
        onLoadFail: (() -> Unit)? = null
    ) {
        mAdLoader = AdLoader.Builder(context, idNativeAds).forNativeAd {
            nativeAd?.destroy()
            nativeAd = it
            onLoadSuccess?.invoke(it)
        }.withAdListener(object : AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                val request = AdRequest.Builder().build()
                mAdLoader?.loadAd(request)
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                nativeAd = null
                onLoadFail?.invoke()
            }
        }).build()
        val request = AdRequest.Builder().build()
        mAdLoader?.loadAd(request)
    }
}