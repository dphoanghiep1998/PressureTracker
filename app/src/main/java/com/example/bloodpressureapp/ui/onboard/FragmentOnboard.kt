package com.example.bloodpressureapp.ui.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.utils.navigateToPage
import com.example.bloodpressureapp.databinding.FragmentOnboardBinding
import com.example.bloodpressureapp.ui.onboard.adapter.ViewPagerAdapter
import com.example.bloodpressureapp.ui.onboard.child_fragment.FragmentHealth
import com.example.bloodpressureapp.ui.onboard.child_fragment.FragmentPressure
import com.example.bloodpressureapp.ui.onboard.child_fragment.FragmentTool

class FragmentOnboard : Fragment() {
    private lateinit var binding: FragmentOnboardBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initButton()
        initViewPager()
        firstInit()

    }

    private fun initButton() {
        binding.btnBack.setOnClickListener{
            val currentItem = binding.vpOnboard.currentItem
            binding.vpOnboard.currentItem = currentItem - 1
        }
        binding.btnNext.setOnClickListener{
            val currentItem = binding.vpOnboard.currentItem
            binding.vpOnboard.currentItem = currentItem + 1
        }
        binding.btnStart.setOnClickListener{
            navigateToPage(R.id.action_fragmentOnboard_to_fragmentMain)
        }
        binding.btnSkip.setOnClickListener{
            navigateToPage(R.id.action_fragmentOnboard_to_fragmentMain)
        }
    }

    private fun firstInit(){
        binding.btnBack.visibility = View.GONE
        binding.btnStart.visibility = View.GONE
        binding.btnNext.visibility = View.VISIBLE
        binding.btnSkip.visibility = View.VISIBLE
    }

    private fun initViewPager() {
        val fragmentList = arrayListOf(
            FragmentTool(),
            FragmentHealth(),
            FragmentPressure()
        )
        val adapter = ViewPagerAdapter(
            fragmentList, childFragmentManager,
            lifecycle
        )
        binding.vpOnboard.adapter = adapter
        binding.dotsIndicator.attachTo(binding.vpOnboard)



        binding.vpOnboard.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        firstInit()
                    }
                    1 -> {
                        binding.btnStart.visibility = View.GONE
                        binding.btnNext.visibility = View.VISIBLE
                        binding.btnBack.visibility = View.VISIBLE
                        binding.btnSkip.visibility = View.VISIBLE
                    }
                    2 -> {
                        binding.btnStart.visibility = View.VISIBLE
                        binding.btnNext.visibility = View.GONE
                        binding.btnBack.visibility = View.VISIBLE
                        binding.btnSkip.visibility = View.GONE

                    }
                }
            }
        })
    }


}