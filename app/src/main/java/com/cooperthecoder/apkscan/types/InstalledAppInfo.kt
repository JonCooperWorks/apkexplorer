package com.cooperthecoder.apkscan.types

import android.content.pm.*
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import java.io.File
import java.io.Serializable

data class InstalledAppInfo(
    val appName: String,
    val packageName: String,
    val activities: Array<ActivityInfo>,
    val receivers: Array<ActivityInfo>,
    val services: Array<ServiceInfo>,
    val providers: Array<ProviderInfo>,
    val publicFiles: Array<File>,
    val sharedLibraries: Array<String>,
    val permissions: Array<PermissionInfo>,
    val version: String,
    val icon: Bitmap,
    val applicationInfo: ApplicationInfo,
    val nativeLibraries: Array<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArray(ActivityInfo.CREATOR)!!,
        parcel.createTypedArray(ActivityInfo.CREATOR)!!,
        parcel.createTypedArray(ServiceInfo.CREATOR)!!,
        parcel.createTypedArray(ProviderInfo.CREATOR)!!,
        parcel.createStringArray()!!.map { filename ->
            File(filename)
        }.toTypedArray(),
        parcel.createStringArray()!!,
        parcel.createTypedArray(PermissionInfo.CREATOR)!!,
        parcel.readString()!!,
        parcel.readParcelable(Bitmap::class.java.classLoader)!!,
        parcel.readParcelable(ApplicationInfo::class.java.classLoader)!!,
        parcel.createStringArray()!!
    ) {
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InstalledAppInfo

        if (appName != other.appName) return false
        if (packageName != other.packageName) return false
        if (!activities.contentEquals(other.activities)) return false
        if (!receivers.contentEquals(other.receivers)) return false
        if (!services.contentEquals(other.services)) return false
        if (!providers.contentEquals(other.providers)) return false
        if (!publicFiles.contentEquals(other.publicFiles)) return false
        if (!sharedLibraries.contentEquals(other.sharedLibraries)) return false
        if (!permissions.contentEquals(other.permissions)) return false
        if (version != other.version) return false
        if (icon != other.icon) return false
        if (applicationInfo != other.applicationInfo) return false
        if (!nativeLibraries.contentEquals(other.nativeLibraries)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = appName.hashCode()
        result = 31 * result + packageName.hashCode()
        result = 31 * result + activities.contentHashCode()
        result = 31 * result + receivers.contentHashCode()
        result = 31 * result + services.contentHashCode()
        result = 31 * result + providers.contentHashCode()
        result = 31 * result + publicFiles.hashCode()
        result = 31 * result + sharedLibraries.contentHashCode()
        result = 31 * result + permissions.contentHashCode()
        result = 31 * result + version.hashCode()
        result = 31 * result + icon.hashCode()
        result = 31 * result + applicationInfo.hashCode()
        result = 31 * result + nativeLibraries.hashCode()
        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(appName)
        parcel.writeString(packageName)
        parcel.writeTypedArray(activities, flags)
        parcel.writeTypedArray(receivers, flags)
        parcel.writeTypedArray(services, flags)
        parcel.writeTypedArray(providers, flags)
        parcel.writeStringArray(
            publicFiles.map { file ->
                file.absolutePath
            }.toTypedArray()
        )
        parcel.writeStringArray(sharedLibraries)
        parcel.writeTypedArray(permissions, flags)
        parcel.writeString(version)
        parcel.writeParcelable(icon, flags)
        parcel.writeParcelable(applicationInfo, flags)
        parcel.writeStringArray(nativeLibraries)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InstalledAppInfo> {
        override fun createFromParcel(parcel: Parcel): InstalledAppInfo {
            return InstalledAppInfo(parcel)
        }

        override fun newArray(size: Int): Array<InstalledAppInfo?> {
            return arrayOfNulls(size)
        }
    }

}