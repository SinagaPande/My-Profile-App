package com.itera.profileapp

expect class DeviceInfo {
    fun getDeviceName(): String
    fun getOsVersion(): String
    fun getDeviceModel(): String
}