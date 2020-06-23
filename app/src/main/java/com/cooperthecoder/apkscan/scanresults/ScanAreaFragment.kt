package com.cooperthecoder.apkscan.scanresults

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cooperthecoder.apkscan.R
import com.cooperthecoder.apkscan.types.InstalledAppInfo
import com.cooperthecoder.apkscan.types.SectionName
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_scan_area.*

class ScanAreaFragment : Fragment(R.layout.fragment_scan_area) {

    companion object {
        const val INSTALLED_APP_INFO = "installed_app_info"
        const val SECTION_NAME = "section_name"

        fun create(sectionName: String, installedAppInfo: InstalledAppInfo): Fragment {
            return ScanAreaFragment().apply {
                arguments = Bundle().apply {
                    putString(SECTION_NAME, sectionName)
                    putParcelable(INSTALLED_APP_INFO, installedAppInfo)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val installedAppInfo =
            requireArguments().getParcelable<InstalledAppInfo>(INSTALLED_APP_INFO)!!
        val sectionName = requireArguments().getString(SECTION_NAME)!!
        contentRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = SectionedRecyclerViewAdapter()
        contentRecyclerView.adapter = adapter

        when (sectionName) {
            SectionName.ACTIVITIES -> {
                adapter.addSection(ActivitySection(installedAppInfo.activities))
            }

            SectionName.SERVICES -> {
                adapter.addSection(ServiceSection(installedAppInfo.services))
            }

            SectionName.RECEIVERS -> {
                adapter.addSection(ReceiverSection(installedAppInfo.receivers))
            }

            SectionName.PROVIDERS -> {
                adapter.addSection(ProviderSection(installedAppInfo.providers))
            }

            SectionName.FILES -> {
                adapter.addSection(FileSection(installedAppInfo.publicFiles))
            }

            SectionName.SHARED_LIBRARIES -> {
                adapter.addSection(SharedLibrarySection(installedAppInfo.sharedLibraries))
            }

            SectionName.PERMISSIONS -> {
                adapter.addSection(PermissionSection(installedAppInfo.permissions))
            }
        }
    }
}