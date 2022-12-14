package com.example.bloodpressureapp.ui.language.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.utils.clickWithDebounce
import com.example.bloodpressureapp.common.utils.supportDisplayLang
import com.example.bloodpressureapp.common.utils.supportedLanguages
import com.example.bloodpressureapp.databinding.ItemLanguageBinding
import java.util.*

interface TouchLanguageListener {
    fun onClickLanguage(locale: Locale)
}

class LanguageAdapter(val context: Context, private val listener: TouchLanguageListener) :
    RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {
    private val mLanguageList = supportedLanguages()
    private var selectedLanguageIndex = -1

    inner class LanguageViewHolder(val binding: ItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding =
            ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(binding)
    }

    fun setCurrentLanguage(language: String) {
        mLanguageList.forEachIndexed { index, l ->
            if (l.language == language) {
                notifyItemChanged(selectedLanguageIndex)
                selectedLanguageIndex = index
                notifyItemChanged(selectedLanguageIndex)

            }
        }
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        with(holder) {
            if (this.adapterPosition == selectedLanguageIndex) {
                binding.tvCountryName.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.neutral_01
                    )
                )
                binding.containerContentLanguage.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.primary
                    )
                )
            } else {
                binding.tvCountryName.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.neutral_04
                    )
                )
                binding.containerContentLanguage.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.neutral_01
                    )
                )
            }
            with(mLanguageList[position]) {
                binding.tvCountryName.text = context.getString(supportDisplayLang()[position].first)
                binding.imvFlag.setImageDrawable(context.getDrawable(supportDisplayLang()[position].second))
                binding.root.clickWithDebounce {
                    notifyItemChanged(selectedLanguageIndex)
                    selectedLanguageIndex = adapterPosition
                    notifyItemChanged(selectedLanguageIndex)
                    listener.onClickLanguage(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mLanguageList.size
    }
}