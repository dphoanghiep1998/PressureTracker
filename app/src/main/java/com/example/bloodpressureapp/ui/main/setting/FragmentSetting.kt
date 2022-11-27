package com.example.bloodpressureapp.ui.main.setting

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.Constant
import com.example.bloodpressureapp.common.utils.getDrawable
import com.example.bloodpressureapp.data.database.file_provider.DatabaseHelper
import com.example.bloodpressureapp.databinding.FragmentSettingBinding
import com.example.bloodpressureapp.dialog.feed_back.DialogFeedBack
import com.example.bloodpressureapp.dialog.feed_back.FeedBackCallBack
import com.example.bloodpressureapp.dialog.rate_us.DialogRateUs
import com.example.bloodpressureapp.dialog.rate_us.RateCallBack
import com.example.bloodpressureapp.ui.main.tracker.model.HistoryModel
import com.example.bloodpressureapp.viewmodel.AppViewModel


class FragmentSetting : Fragment(), FeedBackCallBack, RateCallBack {
    private lateinit var binding: FragmentSettingBinding
    private val viewModel: AppViewModel by activityViewModels()
    private val listHistory = mutableListOf<HistoryModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()
        observeListHistory()
    }

    private fun observeListHistory() {
        viewModel.getAllHistory().observe(viewLifecycleOwner) {
            listHistory.clear()
            listHistory.addAll(it)
        }
    }


    private fun initView() {
        with(binding.containerExportFile) {
            this.imvIcon.setImageDrawable(getDrawable(R.drawable.ic_export_file))
            this.tvTitle.text = getText(R.string.export_file_setting)
        }
        with(binding.containerFeedBack) {
            this.imvIcon.setImageDrawable(getDrawable(R.drawable.ic_feed_back))
            this.tvTitle.text = getText(R.string.feed_back_setting)
        }
        with(binding.containerPrivacy) {
            this.imvIcon.setImageDrawable(getDrawable(R.drawable.ic_privacy_policy))
            this.tvTitle.text = getText(R.string.privacy_policy)
        }
        with(binding.containerRate) {
            this.imvIcon.setImageDrawable(getDrawable(R.drawable.ic_rate_us))
            this.tvTitle.text = getText(R.string.rate_us)
        }
        with(binding.containerLanguage) {
            this.imvIcon.setImageDrawable(getDrawable(R.drawable.ic_language))
            this.tvTitle.text = getText(R.string.language_title)
        }
        with(binding.containerShare) {
            this.imvIcon.setImageDrawable(getDrawable(R.drawable.ic_share))
            this.tvTitle.text = getText(R.string.share)
        }
    }

    private fun initAction() {
        binding.containerLanguage.root.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean(Constant.KEY_FROM_SETTING, true)
            findNavController().navigate(R.id.action_fragmentSetting_to_fragmentLanguage2, bundle)
        }
        binding.containerShare.root.setOnClickListener {
            shareApp()
        }
        binding.containerFeedBack.root.setOnClickListener {
            val dialogFeedBack = DialogFeedBack(this)
            dialogFeedBack.show(childFragmentManager, dialogFeedBack.tag)
        }
        binding.containerPrivacy.root.setOnClickListener {
            openLink(Constant.URL_PRIVACY)
        }
        binding.containerExportFile.root.setOnClickListener {
            val databaseHelper = DatabaseHelper(requireContext())
            databaseHelper.exportDatabaseToCSVFile(listHistory)
        }
        binding.containerRate.root.setOnClickListener {
            val dialogRate = DialogRateUs(this)
            dialogRate.show(childFragmentManager, dialogRate.tag)
        }
    }

    private fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, Constant.CONTENT_SHARE_APP)
            startActivity(Intent.createChooser(shareIntent, "Choose one"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
//
//    private fun feedBack(message: String) {
//        val intent = Intent(Intent.ACTION_SENDTO).apply {
//            setDataAndType(Uri.parse("mailto:"), "message/rfc822")
//            putExtra(Intent.EXTRA_EMAIL, arrayOf("", Constant.MAIL_TO))
//            putExtra(
//                Intent.EXTRA_SUBJECT,
//                "${getString(R.string.app_name)} - SDK_CLIENT ${Build.VERSION.SDK_INT} /n${message}"
//            )
//
//        }
//        startActivity(intent)
//    }

    private fun sendEmail(content: String) {
        val to = arrayOf("", Constant.MAIL_TO)
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "message/rfc822"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        emailIntent.putExtra(
            Intent.EXTRA_SUBJECT,
            "${getString(R.string.app_name)} - SDK_CLIENT ${Build.VERSION.SDK_INT}"
        )
        emailIntent.putExtra(Intent.EXTRA_TEXT, content);
        try {
            startActivity(Intent.createChooser(emailIntent, getString(R.string.app_name)))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(requireContext(), getString(R.string.no_sender_found), Toast.LENGTH_SHORT).show()
        }
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


    override fun onFeedBack(message: String) {
        sendEmail(message)
    }

    override fun onNegativePressed() {
        viewModel.userActionRate = true
    }

    override fun onPositivePressed(star: Int) {
        viewModel.userActionRate = true
        if (star == 5) {
            openLink(Constant.URL_APP)
        }
    }


}