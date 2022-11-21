package com.example.bloodpressureapp.activities

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.bloodpressureapp.common.share_preference.AppSharePreference
import com.example.bloodpressureapp.common.utils.createContext
import com.example.bloodpressureapp.databinding.ActivityMainBinding
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

    private fun initNavController() {
        navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        val settingLanguageLocale = viewModel.settingLanguageLocale
        val languageLocaleChanged = AppSharePreference.INSTANCE.getSavedLanguage(
            Locale.getDefault().language
        ) != settingLanguageLocale
//        if (languageLocaleChanged) {
//            finish()
//            startActivity(intent)
//        }
    }
}