package com.example.bloodpressureapp.ui.main.tracker.add_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bloodpressureapp.BuildConfig
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.Constant
import com.example.bloodpressureapp.common.utils.clickWithDebounce
import com.example.bloodpressureapp.common.utils.navigateToPage
import com.example.bloodpressureapp.databinding.FragmentHistoryBinding
import com.example.bloodpressureapp.ui.main.tracker.adapter.HistoryAdapter
import com.example.bloodpressureapp.ui.main.tracker.adapter.ItemHelper
import com.example.bloodpressureapp.ui.main.tracker.model.HistoryModel
import com.example.bloodpressureapp.viewmodel.AppViewModel
import com.gianghv.libads.NativeAdsManager

class FragmentHistory : Fragment(), ItemHelper {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private val viewModel: AppViewModel by activityViewModels()
    private lateinit var mNativeAdManager: NativeAdsManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        initAds()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeListHistory()
        changeBackPressCallBack()
    }

    private fun observeListHistory() {
        viewModel.getAllHistoryDesc().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.containerEmpty.visibility = View.VISIBLE
                binding.rcvHistory.visibility = View.GONE
                binding.containerFloatButton.visibility = View.VISIBLE
            } else {
                binding.containerEmpty.visibility = View.GONE
                binding.rcvHistory.visibility = View.VISIBLE
                binding.containerFloatButton.visibility = View.GONE

            }
            adapter.setData(it.toMutableList())
        }
    }


    private fun initView() {
        initRecycleView()
        initButton()
    }

    private fun initButton() {
        binding.btnBack.clickWithDebounce {
            findNavController().popBackStack()
        }

        binding.floatingActionButton.clickWithDebounce {
            navigateToPage(R.id.action_fragmentHistory_to_addHistoryFragment)
        }
    }

    private fun initRecycleView() {
        adapter = HistoryAdapter(this)
        adapter.setExpand(true)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rcvHistory.adapter = adapter
        binding.rcvHistory.layoutManager = linearLayoutManager
    }

    override fun onClickEdit(item: HistoryModel) {
        val bundle = Bundle()
        bundle.putParcelable(Constant.KEY_HISTORY_MODEL, item)
        findNavController().navigate(R.id.action_fragmentHistory_to_addHistoryFragment, bundle)
    }

    private fun changeBackPressCallBack() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun initAds() {
        mNativeAdManager = NativeAdsManager(
            requireActivity(),
            BuildConfig.native_history_id1,
            BuildConfig.native_history_id2,
            BuildConfig.native_history_id3,
        )
        binding.nativeAdMediumView.showShimmer(true)
        mNativeAdManager.loadAds(onLoadSuccess = {
            binding.nativeAdMediumView.showShimmer(false)
            binding.nativeAdMediumView.setNativeAd(it)
            binding.nativeAdMediumView.isVisible = true
        }, onLoadFail = {
            binding.nativeAdMediumView.errorShimmer()
        })
    }

}