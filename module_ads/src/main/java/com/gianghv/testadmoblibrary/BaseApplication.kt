package com.gianghv.testadmoblibrary

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.gianghv.libads.AppOpenAdManager
import com.gianghv.libads.InterstitialPreloadAdManager
import com.gianghv.libads.InterstitialSingleReqAdManager
import com.gianghv.libads.utils.Constants
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration


open class BaseApplication : Application(), Application.ActivityLifecycleCallbacks,
    LifecycleObserver {
    companion object {
        lateinit var appContext: Context
    }

    private var currentActivity: Activity? = null
    private var mInterAdManager: InterstitialPreloadAdManager? = null
    private var appOpenAdManager: AppOpenAdManager? = null
    override fun onCreate() {
        super.onCreate()
        appContext = this
        this.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        MobileAds.initialize(this) { MobileAds.setAppMuted(true) }
        val requestConfiguration =
            RequestConfiguration.Builder().setTestDeviceIds(Constants.testDevices()).build()
        MobileAds.setRequestConfiguration(requestConfiguration)
        mInterAdManager = InterstitialPreloadAdManager(
            this,
            getString(R.string.admob_interstitial_id_01),
            getString(R.string.admob_interstitial_id_02),
            getString(R.string.admob_interstitial_id_03)
        )
        mInterAdManager?.initAds()

        appOpenAdManager = AppOpenAdManager(
            this,
            getString(R.string.admob_openad_id_01),
            getString(R.string.admob_openad_id_02),
            getString(R.string.admob_openad_id_03)
        )
        appOpenAdManager?.loadAd()
    }

    fun getInterstitialAds() = mInterAdManager


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected fun onMoveToForeground() {
        currentActivity?.let {
            appOpenAdManager?.showAdIfAvailable(it)
        }
    }

    /** ActivityLifecycleCallback methods.  */
    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        if (appOpenAdManager?.isShowingAd == false) {
            currentActivity = activity
        }
    }

    override fun onActivityResumed(p0: Activity) {
        currentActivity = p0
    }

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityDestroyed(p0: Activity) {
    }

}