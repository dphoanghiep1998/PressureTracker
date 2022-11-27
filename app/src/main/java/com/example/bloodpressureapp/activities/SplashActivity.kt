package com.example.bloodpressureapp.activities

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.DecelerateInterpolator
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        navigateMain()

    }

    private fun navigateMain(){

            val i = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(i)
            finish()

    }

    private fun initView(){
        binding.signalLottie.repeatCount = LottieDrawable.INFINITE
    }

}