package com.example.bloodpressureapp.ui.language

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.Constant
import com.example.bloodpressureapp.common.share_preference.AppSharePreference
import com.example.bloodpressureapp.common.utils.navigateToPage
import com.example.bloodpressureapp.common.utils.supportedLanguages
import com.example.bloodpressureapp.databinding.FragmentLanguageBinding
import com.example.bloodpressureapp.ui.language.adapter.LanguageAdapter
import com.example.bloodpressureapp.ui.language.adapter.TouchLanguageListener
import java.util.*


class FragmentLanguage : Fragment(), TouchLanguageListener {
    private lateinit var binding: FragmentLanguageBinding
    private lateinit var adapter: LanguageAdapter
    private var selectedLocale: Locale = supportedLanguages()[0]


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLanguageBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromBundle()
        initView()
        changeBackPressCallBack()
    }

    private fun deliveryFlow() {
        val initFirstDone = AppSharePreference.INSTANCE.getInitDone(false)
        if (initFirstDone) {
            findNavController().navigate(R.id.action_fragmentLanguage_to_fragmentMain)
        }
    }

    private fun getDataFromBundle() {
        initButton()
        val bundle = this.arguments
        bundle?.let {
            val isFromSetting = it.getBoolean(Constant.KEY_FROM_SETTING)
            if (isFromSetting) {
                binding.btnCheck.setOnClickListener {
                    AppSharePreference.INSTANCE.saveLanguage(selectedLocale.language)
                    findNavController().popBackStack()
                }
            }
        }
        if(bundle == null){
            deliveryFlow()
        }
    }

    private fun initView() {
        initRecycleView()
    }


    private fun initButton() {
        binding.btnCheck.setOnClickListener {
            AppSharePreference.INSTANCE.saveLanguage(selectedLocale.language)
            navigateToPage(R.id.action_fragmentLanguage_to_fragmentOnboard)
        }
    }

    private fun initRecycleView() {
        adapter = LanguageAdapter(requireContext(), this)
        adapter.setCurrentLanguage(getCurrentLanguage())
        binding.rcvLanguage.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvLanguage.adapter = adapter
    }

    private fun getCurrentLanguage(): String {
        return AppSharePreference.INSTANCE.getSavedLanguage(Locale.getDefault().language)
    }

    override fun onClickLanguage(locale: Locale) {
        selectedLocale = locale
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