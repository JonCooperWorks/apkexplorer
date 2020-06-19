package com.cooperthecoder.apkscan.types

import android.content.pm.ApplicationInfo

class SortableApplicationInfo(applicationInfo: ApplicationInfo) :
    ApplicationInfo(applicationInfo), Comparable<ApplicationInfo> {

    companion object {
        fun sortableList(input: List<ApplicationInfo>): List<SortableApplicationInfo> {
            return input.map { applicationInfo ->
                SortableApplicationInfo(applicationInfo)
            }
        }
    }

    override fun compareTo(other: ApplicationInfo): Int {
        return packageName.compareTo(other.packageName)
    }
}