package com.itera.profileapp

expect class NetworkMonitor {
    fun isOnline(): Boolean
}