package com.cooperthecoder.apkscan.types

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

class SortableApplicationInfo(private val pm: PackageManager, applicationInfo: ApplicationInfo) :
    ApplicationInfo(applicationInfo), Comparable<ApplicationInfo> {

    companion object {
        fun sortedList(pm: PackageManager, input: List<ApplicationInfo>): List<ApplicationInfo> {
            return input.map { applicationInfo ->
                SortableApplicationInfo(pm, applicationInfo)
            }.sorted()
        }
    }

    override fun compareTo(other: ApplicationInfo): Int {
        val label = loadLabel(pm).toString()
        val otherLabel = other.loadLabel(pm).toString()
        return label.compareTo(otherLabel)
    }
}