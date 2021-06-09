package com.example.locatecityshops.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class Util {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val networks = connectivityManager.allNetworks
            networks.forEach { network ->
                val info = connectivityManager.getNetworkInfo(network)
            }
        } else {
            val networks = connectivityManager.allNetworkInfo
            networks.forEach { networkInfo ->
                if (networkInfo != null && networkInfo.isConnected) return true
            }
        }
        return true
    }
}