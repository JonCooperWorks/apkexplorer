package com.cooperthecoder.apkscan.utils

import android.content.pm.ActivityInfo
import android.content.pm.ComponentInfo
import android.content.pm.ProviderInfo
import android.content.pm.ServiceInfo
import java.lang.IllegalArgumentException

object BinaryFlagUtils {
    fun getEnabledFlags(componentInfo: ComponentInfo): List<String> {
        val flags = when (componentInfo) {
            is ActivityInfo -> {
                activityFlagNames
            }

            is ProviderInfo -> {
                providerFlagNames
            }

            is ServiceInfo -> {
                serviceFlagNames
            }

            else -> {
                throw IllegalArgumentException("Only ActivityInfo, ProviderInfo and ServiceInfo are supported at this time.")
            }
        }

        // flags isn't on the ComponentInfo class so we have to pull it out for each kind.
        val flagValue = when(componentInfo) {
            is ActivityInfo -> {
                componentInfo.flags
            }

            is ProviderInfo -> {
                componentInfo.flags
            }

            is ServiceInfo -> {
                componentInfo.flags
            }
            else -> {
                throw IllegalArgumentException("Only ActivityInfo, ProviderInfo and ServiceInfo are supported at this time.")
            }
        }

        val enabledFlags = ArrayList<String>()
        for ((flag, name) in flags.iterator()) {
           if ((flagValue and flag) == flag) {
               enabledFlags.add(name)
           }
        }
        return enabledFlags
    }

    private val activityFlagNames = mapOf<Int, String>(
        Pair(64, "FLAG_ALLOW_TASK_REPARENTING"),
        Pair(8, "FLAG_ALWAYS_RETAIN_TASK_STATE"),
        Pair(8192, "FLAG_AUTO_REMOVE_FROM_RECENTS"),
        Pair(4, "FLAG_CLEAR_TASK_ON_LAUNCH"),
        Pair(32768, "FLAG_ENABLE_VR_MODE"),
        Pair(32, "FLAG_ENABLE_VR_MODE"),
        Pair(256, "FLAG_FINISH_ON_CLOSE_SYSTEM_DIALOGS"),
        Pair(2, "FLAG_FINISH_ON_TASK_LAUNCH"),
        Pair(512, "FLAG_HARDWARE_ACCELERATED"),
        Pair(2048, "FLAG_HARDWARE_ACCELERATED"),
        Pair(1, "FLAG_MULTIPROCESS"),
        Pair(128, "FLAG_NO_HISTORY"),
        Pair(33554432, "FLAG_PREFER_MINIMAL_POST_PROCESSING"),
        Pair(4096, "FLAG_RELINQUISH_TASK_IDENTITY"),
        Pair(16384, "FLAG_RESUME_WHILE_PAUSING"),
        Pair(1073741824, "FLAG_SINGLE_USER"),
        Pair(16, "FLAG_STATE_NOT_NEEDED")
    )

    private val serviceFlagNames = mapOf(
        Pair(4, "FLAG_EXTERNAL_SERVICE"),
        Pair(2, "FLAG_ISOLATED_PROCESS"),
        Pair(1073741824, "FLAG_ISOLATED_PROCESS"),
        Pair(1, "FLAG_STOP_WITH_TASK"),
        Pair(8, "FLAG_USE_APP_ZYGOTE")
    )

    private val providerFlagNames = mapOf(
        Pair(1073741824, "FLAG_SINGLE_USER")
    )
}