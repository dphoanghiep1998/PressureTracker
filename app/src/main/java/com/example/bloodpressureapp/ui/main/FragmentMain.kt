package com.example.bloodpressureapp.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.Constant
import com.example.bloodpressureapp.databinding.FragmentMainBinding
import com.example.bloodpressureapp.dialog.rate_us.DialogRateUs
import com.example.bloodpressureapp.dialog.rate_us.RateCallBack
import com.example.bloodpressureapp.ui.main.info.FragmentInformation
import com.example.bloodpressureapp.ui.main.setting.FragmentSetting
import com.example.bloodpressureapp.ui.main.tracker.FragmentTracker
import com.example.bloodpressureapp.ui.onboard.adapter.ViewPagerAdapter
import com.example.bloodpressureapp.viewmodel.AppViewModel
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView


class FragmentMain : Fragment(), RateCallBack {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: AppViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setStatusColor()
        changeBackPressCallBack()
    }

    private fun initView() {
        initBottomNav()
        initViewPager()
    }

    private fun setStatusColor() {
        val window = requireActivity().window

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.primary)
    }


    private fun initViewPager() {
        val fragmentList = arrayListOf(
            FragmentInformation(),
            FragmentTracker(),
            FragmentSetting()
        )
        val adapter = ViewPagerAdapter(
            fragmentList, childFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false;
        binding.viewPager.currentItem = 1

    }

    @SuppressLint("RestrictedApi")
    private fun initBottomNav() {
        binding.navBottom.menu.getItem(1).isChecked = true
        val bottomNavigationMenuView =
            binding.navBottom.getChildAt(0) as BottomNavigationMenuView

        for (i in 0..2) {
            (bottomNavigationMenuView.getChildAt(i) as BottomNavigationItemView).setIconTintList(
                null
            )
            if (i == 1) {
                (bottomNavigationMenuView.getChildAt(i) as BottomNavigationItemView).setIconSize(
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 60f,
                        resources.displayMetrics
                    ).toInt()
                )
            } else {
                (bottomNavigationMenuView.getChildAt(i) as BottomNavigationItemView).setIconSize(
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 30f,
                        resources.displayMetrics
                    ).toInt()
                )
            }

        }
        binding.navBottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragmentInformation -> {
                    binding.viewPager.currentItem = 0
                    return@setOnItemSelectedListener true
                }
                R.id.fragmentTracker -> {
                    binding.viewPager.currentItem = 1
                    return@setOnItemSelectedListener true
                }
                R.id.fragmentSetting -> {
                    binding.viewPager.currentItem = 2
                    return@setOnItemSelectedListener true
                }
            }
            true
        }

    }

    private fun changeBackPressCallBack() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (viewModel.userActionRate) {
                        requireActivity().finishAffinity()
                    } else {
                        val dialogRateUs = DialogRateUs(this@FragmentMain)
                        dialogRateUs.show(childFragmentManager, dialogRateUs.tag)
                    }

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }


    private fun openLink(strUri: String?) {
        try {
            val uri = Uri.parse(strUri)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onNegativePressed() {
        requireActivity().finishAffinity()
    }

    override fun onPositivePressed(star: Int) {
        if (star == 5) {
            openLink(Constant.URL_APP)
        }
        Toast.makeText(requireContext(), getString(R.string.rate_success), Toast.LENGTH_SHORT)
            .show()
        requireActivity().finishAffinity()
    }


}