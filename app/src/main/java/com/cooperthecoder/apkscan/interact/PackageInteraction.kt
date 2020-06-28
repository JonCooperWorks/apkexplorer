package com.cooperthecoder.apkscan.interact

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build

class PackageInteraction(private val context: Context) {
    fun startActivity(intent: Intent) {
        context.startActivity(intent)
    }

    fun startService(intent: Intent): ComponentName? {
        return context.startService(intent)
    }

    fun startForegroundService(intent: Intent): ComponentName? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            return context.startService(intent)
        }
    }

    fun sendBroadcast(intent: Intent, receiverPermission: String? = null) {
        context.sendBroadcast(intent, receiverPermission)
    }
}