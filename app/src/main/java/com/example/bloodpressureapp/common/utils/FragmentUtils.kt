package com.example.bloodpressureapp.common.utils

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.AutoTransition
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView


fun Fragment.navigateToPage(actionId: Int) {
    findNavController().navigate(actionId)
}

fun Fragment.getColor(resId: Int): Int {
    return ContextCompat.getColor(requireContext(), resId)
}

fun Fragment.getDrawable(resId: Int): Drawable {
    return ContextCompat.getDrawable(requireContext(), resId)!!
}

fun Fragment.toDp(sizeInDp: Int): Int {
    val scale: Float = requireContext().resources.displayMetrics.density
    return (sizeInDp * scale + 0.5f).toInt()
}

fun BottomNavigationView.fixBlinking() {
    val menuView = getChildAt(0) as BottomNavigationMenuView
    with(menuView::class.java.getDeclaredField("set")) {
        isAccessible = true
        set(menuView, AutoTransition().apply { duration = 0L })
    }
}
