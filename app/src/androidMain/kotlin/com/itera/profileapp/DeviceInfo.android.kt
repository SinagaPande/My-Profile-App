package com.itera.profileapp

import android.os.Build

actual class DeviceInfo {
    actual fun getDeviceName(): String = Build.MANUFACTURER
    actual fun getOsVersion(): String = "Android ${Build.VERSION.SDK_INT}"
    actual fun getDeviceModel(): String = Build.MODEL
}