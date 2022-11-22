package com.example.bloodpressureapp.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.databinding.FragmentMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView


class FragmentMain : Fragment() {
    private lateinit var binding: FragmentMainBinding

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
    }

    private fun initView() {
        initBottomNav()
        initControllerNav()
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
            if(i == 1){
                (bottomNavigationMenuView.getChildAt(i) as BottomNavigationItemView).setIconSize(
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 60f,
                        resources.displayMetrics
                    ).toInt()
                )
            }else{
                (bottomNavigationMenuView.getChildAt(i) as BottomNavigationItemView).setIconSize(
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 30f,
                        resources.displayMetrics
                    ).toInt()
                )
            }

        }

    }

}