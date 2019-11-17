package com.jairrab.conversionrateapp.ui.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager

import androidx.annotation.RequiresPermission
import javax.inject.Inject

/**
 * Check device's network connectivity and speed
 *
 * @author emil http://stackoverflow.com/users/220710/emil
 */
class Connectivity @Inject constructor(private val application: Application) {

    val networkInfo: NetworkInfo?
        @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
        get() = getNetworkInfo(application)

    val isConnected: Boolean
        @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
        get() = isConnected(application)

    val isConnectedFast: Boolean
        @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
        get() = isConnectedFast(application)

    val isConnectedMobile: Boolean
        @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
        get() = isConnectedMobile(application)

    val isConnectedWifi: Boolean
        @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
        get() = isConnectedWifi(application)

    companion object {

        /**
         * Get the network info
         *
         * Permissions required by ConnectivityManager.getActiveNetworkInfo:
         * `android.permission.ACCESS_NETWORK_STATE`
         *
         * @param context context or activity
         * @return the [NetworkInfo] NetworkInfo
         */
        @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
        fun getNetworkInfo(context: Context): NetworkInfo? {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo
        }

        /**
         * Check if there is any connectivity.
         *
         * Requires permisission:
         * `android.permission.ACCESS_NETWORK_STATE`
         *
         * @param context context or activity
         * @return true if device is connexted to the network
         */
        @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
        fun isConnected(context: Context): Boolean {
            val info = getNetworkInfo(context)
            return info != null && info.isConnected
        }

        /**
         * Check if there is fast connectivity
         *
         * Requires permisission:
         * `android.permission.ACCESS_NETWORK_STATE`
         *
         * @param context context or activity
         * @return true if connection is a fast network
         */
        @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
        fun isConnectedFast(context: Context): Boolean {
            val info = getNetworkInfo(context)
            return info != null && info.isConnected && isConnectionFast(info.type, info.subtype)
        }

        /**
         * Check if there is any connectivity to a mobile network
         *
         * Requires permisission:
         * `android.permission.ACCESS_NETWORK_STATE`
         *
         * @param context context or activity
         * @return true if connected to mobile
         */
        @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
        fun isConnectedMobile(context: Context): Boolean {
            val info = getNetworkInfo(context)
            return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_MOBILE
        }

        /**
         * Check if there is any connectivity to a Wifi network
         *
         * Requires permisission:
         * `android.permission.ACCESS_NETWORK_STATE`
         *
         * @param context context or activity
         * @return true if connected to wifi
         */
        @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
        fun isConnectedWifi(context: Context): Boolean {
            val info = getNetworkInfo(context)
            return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_WIFI
        }

        /**
         * Check if the connection is fast
         *
         * @param type    type of connectivity as defined by [ConnectivityManager]
         * @param subType connectivity sub type as defined by [TelephonyManager]
         * @return true if connection is a fast network
         */
        fun isConnectionFast(type: Int, subType: Int): Boolean {
            when (type) {
                ConnectivityManager.TYPE_WIFI   -> return true
                ConnectivityManager.TYPE_MOBILE -> when (subType) {
                    TelephonyManager.NETWORK_TYPE_1xRTT   -> return false // ~ 50-100 kbps
                    TelephonyManager.NETWORK_TYPE_CDMA    -> return false // ~ 14-64 kbps
                    TelephonyManager.NETWORK_TYPE_EDGE    -> return false // ~ 50-100 kbps
                    TelephonyManager.NETWORK_TYPE_EVDO_0  -> return true // ~ 400-1000 kbps
                    TelephonyManager.NETWORK_TYPE_EVDO_A  -> return true // ~ 600-1400 kbps
                    TelephonyManager.NETWORK_TYPE_GPRS    -> return false // ~ 100 kbps
                    TelephonyManager.NETWORK_TYPE_HSDPA   -> return true // ~ 2-14 Mbps
                    TelephonyManager.NETWORK_TYPE_HSPA    -> return true // ~ 700-1700 kbps
                    TelephonyManager.NETWORK_TYPE_HSUPA   -> return true // ~ 1-23 Mbps
                    TelephonyManager.NETWORK_TYPE_UMTS    -> return true // ~ 400-7000 kbps
                    /*
                     * Above API level 7, make sure to set android:targetSdkVersion
                     * to appropriate level to use these
                     */
                    TelephonyManager.NETWORK_TYPE_EHRPD // API level 11
                                                          -> return true // ~ 1-2 Mbps
                    TelephonyManager.NETWORK_TYPE_EVDO_B // API level 9
                                                          -> return true // ~ 5 Mbps
                    TelephonyManager.NETWORK_TYPE_HSPAP // API level 13
                                                          -> return true // ~ 10-20 Mbps
                    TelephonyManager.NETWORK_TYPE_IDEN // API level 8
                                                          -> return false // ~25 kbps
                    TelephonyManager.NETWORK_TYPE_LTE // API level 11
                                                          -> return true // ~ 10+ Mbps
                    // Unknown
                    TelephonyManager.NETWORK_TYPE_UNKNOWN -> return false
                    else                                  -> return false
                }
                else                            -> return false
            }
        }
    }
}
