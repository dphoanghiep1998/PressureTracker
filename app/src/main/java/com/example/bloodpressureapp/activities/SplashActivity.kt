package com.example.bloodpressureapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieDrawable
import com.example.bloodpressureapp.BuildConfig
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.Constant
import com.example.bloodpressureapp.databinding.ActivitySplashBinding
import com.gianghv.libads.InterstitialSingleReqAdManager

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private var interstitialSingleReqAdManager: InterstitialSingleReqAdManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initAds()

    }

//    private fun navigateMain(){
//            Handler(Looper.getMainLooper()).postDelayed({
//                val i = Intent(this@SplashActivity, MainActivity::class.java)
//                startActivity(i)
//                finish()
//            },3000L)
//    }

    private fun initView(){
        binding.signalLottie.repeatCount = LottieDrawable.INFINITE
    }
    private fun initAds(){
        interstitialSingleReqAdManager = InterstitialSingleReqAdManager(
            this,
            BuildConfig.inter_splash_id1,
            BuildConfig.inter_splash_id2,
            BuildConfig.inter_splash_id3,

        )
        interstitialSingleReqAdManager?.showAds(this, onAdClose = {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            finish()
        }, onAdLoadFail = {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            finish()
        })
    }

}