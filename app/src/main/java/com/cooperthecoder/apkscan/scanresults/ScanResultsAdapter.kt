package com.cooperthecoder.apkscan.scanresults

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cooperthecoder.apkscan.types.InstalledAppInfo
import com.cooperthecoder.apkscan.types.SectionName

class ScanResultsAdapter(
    fragmentActivity: FragmentActivity,
    private val installedAppInfo: InstalledAppInfo
) : FragmentStateAdapter(fragmentActivity) {

    private val sections = ArrayList<String>()

    init {
        sections.add(SectionName.APP_INFO)
        if (installedAppInfo.activities.isNotEmpty()) {
            sections.add(SectionName.ACTIVITIES)
        }

        if (installedAppInfo.services.isNotEmpty()) {
            sections.add(SectionName.SERVICES)
        }

        if (installedAppInfo.receivers.isNotEmpty()) {
            sections.add(SectionName.RECEIVERS)
        }

        if (installedAppInfo.providers.isNotEmpty()) {
            sections.add(SectionName.PROVIDERS)
        }

        if (installedAppInfo.permissions.isNotEmpty()) {
            sections.add(SectionName.PERMISSIONS)
        }

        if (installedAppInfo.publicFiles.count() != 0) {
            sections.add(SectionName.FILES)
        }

        if (installedAppInfo.sharedLibraries.isNotEmpty()) {
            sections.add(SectionName.SHARED_LIBRARIES)
        }

        if (installedAppInfo.nativeLibraries.isNotEmpty()) {
            sections.add(SectionName.NATIVE_LIBARIES)
        }
    }

    override fun getItemCount(): Int {
        return sections.size
    }

    override fun createFragment(position: Int): Fragment {
        val name = sections[position]
        return ScanTabFragment.create(name, installedAppInfo)
    }

    fun sectionName(position: Int): String {
        return sections[position]
    }
}