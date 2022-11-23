/*
 * WiFiAnalyzer
 * Copyright (C) 2015 - 2022 VREM Software Development <VREMSoftwareDevelopment@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.example.bloodpressureapp.common.utils

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import java.util.*

fun Context.createContext(newLocale: Locale): Context =
    if (buildMinVersionN()) {
        createContextAndroidN(newLocale)
    } else {
        createContextLegacy(newLocale)
    }

private fun Context.createContextAndroidN(newLocale: Locale): Context {
    val resources: Resources = resources
    val configuration: Configuration = resources.configuration
    configuration.setLocale(newLocale)
    return createConfigurationContext(configuration)
}

private fun Context.createContextLegacy(newLocale: Locale): Context {
    val resources: Resources = resources
    val configuration: Configuration = resources.configuration
    configuration.locale = newLocale
    resources.updateConfiguration(configuration, resources.displayMetrics)
    return this
}

@ColorInt
fun Context.compatColor(@ColorRes id: Int): Int =
    if (buildMinVersionM()) {
        getColor(id)
    } else {
        ContextCompat.getColor(this, id)
    }

fun hasHardwareAcceleration(activity: Activity): Boolean {
    // Has HW acceleration been enabled manually in the current window?
    val window: Window = activity.window
    if ((window.attributes.flags
                and WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED) != 0
    ) {
        return true
    }

    // Has HW acceleration been enabled in the manifest?
    try {
        val info: ActivityInfo = activity.getPackageManager().getActivityInfo(
            activity.componentName, 0
        )
        if (info.flags and ActivityInfo.FLAG_HARDWARE_ACCELERATED != 0) {
            return true
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return false
}
fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}