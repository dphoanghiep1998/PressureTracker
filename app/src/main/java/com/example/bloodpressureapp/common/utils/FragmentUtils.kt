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
 fun Fragment.toDp(sizeInDp: Int): Int {
    val scale: Float = requireContext().resources.displayMetrics.density
    return (sizeInDp * scale + 0.5f).toInt()
}
