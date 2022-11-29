package com.example.bloodpressureapp.ui.main.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bloodpressureapp.BuildConfig
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.Constant
import com.example.bloodpressureapp.common.utils.getColor
import com.example.bloodpressureapp.databinding.FragmentInfomationBinding
import com.example.bloodpressureapp.ui.main.info.adapter.InfoAdapter
import com.example.bloodpressureapp.ui.main.info.adapter.ItemTouchListener
import com.example.bloodpressureapp.ui.main.info.adapter.SpacesItemDecoration
import com.gianghv.libads.AdaptiveBannerManager


class FragmentInformation : Fragment(), ItemTouchListener {
    private lateinit var binding: FragmentInfomationBinding
    private lateinit var adapter: InfoAdapter
    private lateinit var adaptiveBannerManager: AdaptiveBannerManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfomationBinding.inflate(layoutInflater)
        initAds()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initRecycleView()
    }

    private fun initRecycleView() {
        val data = mutableListOf(
            DataInfoModel(
                R.drawable.ic_info_icon_1,
                getString(R.string.info_1),
                getColor(R.color.cbp_01)
            ),
            DataInfoModel(
                R.drawable.ic_info_icon_2,
                getString(R.string.info_2),
                getColor(R.color.cbp_02)
            ),
            DataInfoModel(
                R.drawable.ic_info_icon_3,
                getString(R.string.info_3),
                getColor(R.color.cbp_03)
            ),
            DataInfoModel(
                R.drawable.ic_info_icon_4,
                getString(R.string.info_4),
                getColor(R.color.cbp_04)
            ),
            DataInfoModel(
                R.drawable.ic_info_icon_5,
                getString(R.string.info_5),
                getColor(R.color.cbp_05)
            ),
            DataInfoModel(
                R.drawable.ic_info_icon_6,
                getString(R.string.info_6),
                getColor(R.color.cbp_01)
            ),
            DataInfoModel(
                R.drawable.ic_info_icon_7,
                getString(R.string.info_7),
                getColor(R.color.cbp_02)
            ),
        )
        adapter = InfoAdapter(data, this)
        val layoutManager = object : GridLayoutManager(requireContext(), 2) {
            override fun canScrollVertically() = false
        }
        binding.rcvInfo.addItemDecoration(SpacesItemDecoration(18));

        binding.rcvInfo.layoutManager = layoutManager
        binding.rcvInfo.adapter = adapter
    }

    override fun onClickItem(position: Int) {
        when(position){
            0->findNavController().navigate(R.id.action_fragmentMain_to_fragmentInfo1)
            1->findNavController().navigate(R.id.action_fragmentMain_to_fragmentInfo2)
            2->findNavController().navigate(R.id.action_fragmentMain_to_fragmentInfo3)
            3->findNavController().navigate(R.id.action_fragmentMain_to_fragmentInfo4)
            4->findNavController().navigate(R.id.action_fragmentMain_to_fragmentInfo5)
            5->findNavController().navigate(R.id.action_fragmentMain_to_fragmentInfo6)
            6->findNavController().navigate(R.id.action_fragmentMain_to_fragmentInfo7)
        }

    }
    private fun initAds(){
        adaptiveBannerManager = AdaptiveBannerManager(
            requireActivity(),
            BuildConfig.banner_home_id1,
            BuildConfig.banner_home_id2,
            BuildConfig.banner_home_id3,
        )
        adaptiveBannerManager.loadBanner(binding.bannerAds, onAdLoadFail = {
            Toast.makeText(requireContext(), "Load Banner Failer", Toast.LENGTH_SHORT).show()
        })
    }

}