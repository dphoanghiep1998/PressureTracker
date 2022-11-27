package com.example.bloodpressureapp.activities

import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.share_preference.AppSharePreference
import com.example.bloodpressureapp.common.utils.Alarm
import com.example.bloodpressureapp.common.utils.createContext
import com.example.bloodpressureapp.common.utils.navigateToPage
import com.example.bloodpressureapp.databinding.ActivityMainBinding
import com.example.bloodpressureapp.receivers.BootCompleteReceiver
import com.example.bloodpressureapp.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    private var navHostFragment: NavHostFragment? = null
    private lateinit var binding: ActivityMainBinding
    private val viewModel:AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavController()
        setComponent()
    }
    override fun attachBaseContext(newBase: Context) =
        super.attachBaseContext(
            newBase.createContext(
                Locale(
                    AppSharePreference.INSTANCE.getSavedLanguage(
                        Locale.getDefault().language
                    )
                )
            )
        )

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (overrideConfiguration != null) {
            val uiMode = overrideConfiguration.uiMode
            overrideConfiguration.setTo(baseContext.resources.configuration)
            overrideConfiguration.uiMode = uiMode
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }

    private fun setComponent(){
        val receiver = ComponentName(applicationContext,BootCompleteReceiver::class.java)
        applicationContext.packageManager.setComponentEnabledSetting(
            receiver,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )

        Alarm.setAlarm(this)

    }

    private fun initNavController() {
        navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
//        val inflater = navHostFragment!!.navController.navInflater
//        val graph = inflater.inflate(R.navigation.bottom_nav)
//        navHostFragment!!.navController.graph = graph
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        val settingLanguageLocale = viewModel.settingLanguageLocale
        val languageLocaleChanged = AppSharePreference.INSTANCE.getSavedLanguage(
            Locale.getDefault().language
        ) != settingLanguageLocale
        if (languageLocaleChanged) {
            finish()
            startActivity(intent)
        }
    }
}