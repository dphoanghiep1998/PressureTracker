package com.gianghv.testadmoblibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gianghv.libads.InterstitialSingleReqAdManager
import com.gianghv.libads.utils.DialogLoadingAds

class PlashActivity : AppCompatActivity() {
    private var interstitialSingleReqAdManager: InterstitialSingleReqAdManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plash)

        interstitialSingleReqAdManager = InterstitialSingleReqAdManager(
            this,
            getString(R.string.admob_interstitial_id_01),
            getString(R.string.admob_interstitial_id_02),
            getString(R.string.admob_interstitial_id_03)
        )

        interstitialSingleReqAdManager?.showAds(this, onAdClose = {
            startActivity(
                Intent(this@PlashActivity, MainActivity::class.java)
            )
            finish()
        }, onAdLoadFail = {
            startActivity(
                Intent(this@PlashActivity, MainActivity::class.java)
            )
            finish()
        })
    }
}