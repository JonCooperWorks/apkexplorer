package com.cooperthecoder.apkscan.utils

import android.content.pm.*
import java.lang.IllegalArgumentException

object BinaryFlagUtils {
    fun getEnabledFlags(componentInfo: Any): List<String> {
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

            is ApplicationInfo -> {
                appFlagNames
            }

            else -> {
                throw IllegalArgumentException("Only ApplicationInfo, ActivityInfo, ProviderInfo and ServiceInfo are supported at this time.")
            }
        }

        // flags isn't on the ComponentInfo class so we have to pull it out for each kind.
        val flagValue = when (componentInfo) {
            is ActivityInfo -> {
                componentInfo.flags
            }

            is ProviderInfo -> {
                componentInfo.flags
            }

            is ServiceInfo -> {
                componentInfo.flags
            }

            is ApplicationInfo -> {
                componentInfo.flags
            }

            else -> {
                throw IllegalArgumentException("Only ApplicationInfo, ActivityInfo, ProviderInfo and ServiceInfo are supported at this time.")
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

    private val appFlagNames = mapOf(
        Pair(32768, "FLAG_ALLOW_BACKUP"),
        Pair(64, "FLAG_ALLOW_CLEAR_USER_DATA"),
        Pair(32, "FLAG_ALLOW_TASK_REPARENTING"),
        Pair(2, "FLAG_DEBUGGABLE"),
        Pair(262144, "FLAG_EXTERNAL_STORAGE"),
        Pair(268435456, "FLAG_EXTRACT_NATIVE_LIBS"),
        Pair(16, "FLAG_FACTORY_TEST"),
        Pair(67108864, "FLAG_FULL_BACKUP_ONLY"),
        Pair(536870912, "FLAG_HARDWARE_ACCELERATED"),
        Pair(4, "FLAG_HAS_CODE"),
        Pair(8388608, "FLAG_INSTALLED"),
        Pair(16777216, "FLAG_IS_DATA_ONLY"),
        Pair(33554432, "FLAG_IS_GAME"),
        Pair(65536, "FLAG_KILL_AFTER_RESTORE"),
        Pair(1048576, "FLAG_LARGE_HEAP"),
        Pair(-2147483648, "FLAG_MULTIARCH"),
        Pair(8, "FLAG_PERSISTENT"),
        Pair(4096, "FLAG_RESIZEABLE_FOR_SCREENS"),
        Pair(131072, "FLAG_RESTORE_ANY_VERSION"),
        Pair(2097152, "FLAG_STOPPED"),
        Pair(2048, "FLAG_SUPPORTS_LARGE_SCREENS"),
        Pair(1024, "FLAG_SUPPORTS_NORMAL_SCREENS"),
        Pair(4194304, "FLAG_SUPPORTS_RTL"),
        Pair(8192, "FLAG_SUPPORTS_SCREEN_DENSITIES"),
        Pair(512, "FLAG_SUPPORTS_SMALL_SCREENS"),
        Pair(524288, "FLAG_SUPPORTS_XLARGE_SCREENS"),
        Pair(1073741824, "FLAG_SUSPENDED"),
        Pair(1, "FLAG_SYSTEM"),
        Pair(256, "FLAG_TEST_ONLY"),
        Pair(128, "FLAG_UPDATED_SYSTEM_APP"),
        Pair(134217728, "FLAG_USES_CLEARTEXT_TRAFFIC"),
        Pair(16384, "FLAG_VM_SAFE_MODE")
    )
}