package com.example.bloodpressureapp.common.utils

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


fun Fragment.navigateToPage(actionId: Int) {
    findNavController().navigate(actionId)
}
fun Fragment.getColor(resId:Int):Int {
    return ContextCompat.getColor(requireContext(),resId)
}
