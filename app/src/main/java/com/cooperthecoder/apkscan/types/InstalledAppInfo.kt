package com.cooperthecoder.apkscan.types

import android.content.pm.*
import android.graphics.drawable.Drawable
import java.io.File

data class InstalledAppInfo(
    val packageName: String,
    val activities: Array<ActivityInfo>,
    val receivers: Array<ActivityInfo>,
    val services: Array<ServiceInfo>,
    val providers: Array<ProviderInfo>,
    val publicFiles: List<File>,
    val sharedLibraries: Array<String>,
    val permissions: Array<PermissionInfo>,
    val version: String,
    val icon: Drawable
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InstalledAppInfo

        if (packageName != other.packageName) return false
        if (!activities.contentEquals(other.activities)) return false
        if (!receivers.contentEquals(other.receivers)) return false
        if (!services.contentEquals(other.services)) return false
        if (!providers.contentEquals(other.providers)) return false
        if (publicFiles != other.publicFiles) return false
        if (!sharedLibraries.contentEquals(other.sharedLibraries)) return false
        if (!permissions.contentEquals(other.permissions)) return false
        if (version != other.version) return false
        if (icon != other.icon) return false

        return true
    }

    override fun hashCode(): Int {
        var result = packageName.hashCode()
        result = 31 * result + activities.contentHashCode()
        result = 31 * result + receivers.contentHashCode()
        result = 31 * result + services.contentHashCode()
        result = 31 * result + providers.contentHashCode()
        result = 31 * result + publicFiles.hashCode()
        result = 31 * result + sharedLibraries.contentHashCode()
        result = 31 * result + permissions.contentHashCode()
        result = 31 * result + version.hashCode()
        result = 31 * result + icon.hashCode()
        return result
    }

}