package com.gianghv.testadmoblibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.isVisible
import com.gianghv.libads.*

class MainActivity : AppCompatActivity() {
    lateinit var btnShowAdsFull: Button
    lateinit var btnShowNativeBig: Button
    lateinit var btnShowNativeSmall: Button
    lateinit var btnLoadBanner: Button
    lateinit var nativeAdMediumView: NativeAdMediumView
    lateinit var nativeAdSmallView: NativeAdSmallView
    lateinit var frameBanner: FrameLayout

    lateinit var mNativeAdManager: NativeAdsManager
    lateinit var adaptiveBannerManager: AdaptiveBannerManager

    private var interstitialSingleReqAdManager: InterstitialSingleReqAdManager? = null

    override fun onStart() {
        super.onStart()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

        mNativeAdManager = NativeAdsManager(
            this@MainActivity,
            getString(R.string.admob_native_id_01),
            getString(R.string.admob_native_id_02),
            getString(R.string.admob_native_id_03)
        )

        adaptiveBannerManager = AdaptiveBannerManager(
            this@MainActivity,
            getString(R.string.admob_banner_id_01),
            getString(R.string.admob_banner_id_02),
            getString(R.string.admob_banner_id_03)
        )
        interstitialSingleReqAdManager = InterstitialSingleReqAdManager(
            this,
            getString(R.string.admob_interstitial_id_01),
            getString(R.string.admob_interstitial_id_02),
            getString(R.string.admob_interstitial_id_03)
        )

        btnShowAdsFull.setOnClickListener {
            // Nhớ hiển thị loading trước khi show ads này
            interstitialSingleReqAdManager?.showAds(this, onAdClose = {
                //Xử lý action khi show ads thành công
            }, onAdLoadFail = {
                //Xử lý action khi show ads thất bại
            })
        }

        btnShowNativeSmall.setOnClickListener {
            nativeAdSmallView.showShimmer(true)
            mNativeAdManager.loadAds(onLoadSuccess = {
                nativeAdSmallView.showShimmer(false)
                nativeAdSmallView.setNativeAd(it)
                nativeAdSmallView.isVisible = true
            }, onLoadFail = {
                nativeAdSmallView.errorShimmer()
                Toast.makeText(this@MainActivity, "Load Failed", Toast.LENGTH_SHORT).show()
            })
        }

        btnShowNativeBig.setOnClickListener {
            nativeAdMediumView.showShimmer(true)
            mNativeAdManager.loadAds(onLoadSuccess = {
                nativeAdMediumView.showShimmer(false)
                nativeAdMediumView.setNativeAd(it)
                nativeAdMediumView.isVisible = true
            }, onLoadFail = {
                nativeAdMediumView.errorShimmer()
                Toast.makeText(this@MainActivity, "Load Failed", Toast.LENGTH_SHORT).show()
            })
        }

        btnLoadBanner.setOnClickListener {
            adaptiveBannerManager.loadBanner(frameBanner, onAdLoadFail = {
                Toast.makeText(this@MainActivity, "Load Banner Failer", Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun initView() {
        btnShowAdsFull = findViewById(R.id.btnShowAdsFull)
        btnShowNativeBig = findViewById(R.id.btnShowNativeBig)
        btnShowNativeSmall = findViewById(R.id.btnShowNativeSmall)
        nativeAdMediumView = findViewById(R.id.nativeAdMediumView)
        nativeAdSmallView = findViewById(R.id.nativeAdSmallView)
        btnLoadBanner = findViewById(R.id.btnLoadBanner)
        frameBanner = findViewById(R.id.frameBanner)
    }
}