package com.example.bloodpressureapp.ui.main.tracker.add_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.Constant
import com.example.bloodpressureapp.databinding.FragmentHistoryBinding
import com.example.bloodpressureapp.ui.main.tracker.adapter.HistoryAdapter
import com.example.bloodpressureapp.ui.main.tracker.adapter.ItemHelper
import com.example.bloodpressureapp.ui.main.tracker.model.HistoryModel
import com.example.bloodpressureapp.viewmodel.AppViewModel

class FragmentHistory : Fragment(), ItemHelper {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private val viewModel: AppViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeListHistory()
        changeBackPressCallBack()
    }

    private fun observeListHistory() {
        viewModel.getAllHistory().observe(viewLifecycleOwner) {
            if(it.isEmpty()){
                binding.containerEmpty.visibility = View.VISIBLE
                binding.rcvHistory.visibility = View.GONE
            }else{
                binding.containerEmpty.visibility = View.GONE
                binding.rcvHistory.visibility = View.VISIBLE
            }
            adapter.setData(it.toMutableList())
        }
    }


    private fun initView() {
        initRecycleView()
        initButton()
    }

    private fun initButton() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initRecycleView() {
        adapter = HistoryAdapter(this)
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

}