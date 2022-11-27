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
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.Constant
import com.example.bloodpressureapp.databinding.FragmentMainBinding
import com.example.bloodpressureapp.dialog.rate_us.DialogRateUs
import com.example.bloodpressureapp.dialog.rate_us.RateCallBack
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
        initControllerNav()
    }
    private fun setStatusColor(){
        val window = requireActivity().window

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        window.statusBarColor = ContextCompat.getColor(requireActivity(),R.color.primary)
    }

    private fun initControllerNav() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_bottom) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navBottom.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragmentTracker -> showBottomNav()
                R.id.fragmentSetting -> showBottomNav()
                R.id.fragmentInformation -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        binding.navBottom.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.navBottom.visibility = View.GONE
    }

    @SuppressLint("RestrictedApi")
    private fun initBottomNav() {
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
        requireActivity().finishAffinity()
    }


}