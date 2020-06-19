package com.cooperthecoder.apkscan.types

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

class SortableApplicationInfo(private val pm: PackageManager, applicationInfo: ApplicationInfo) :
    ApplicationInfo(applicationInfo), Comparable<ApplicationInfo> {

    companion object {
        fun sortableList(pm: PackageManager, input: List<ApplicationInfo>): List<SortableApplicationInfo> {
            return input.map { applicationInfo ->
                SortableApplicationInfo(pm, applicationInfo)
            }
        }
    }

    override fun compareTo(other: ApplicationInfo): Int {
        val label = loadLabel(pm).toString()
        val otherLabel = other.loadLabel(pm).toString()
        return label.compareTo(otherLabel)
    }
}