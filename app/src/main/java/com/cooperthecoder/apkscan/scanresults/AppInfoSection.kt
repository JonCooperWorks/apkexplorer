package com.cooperthecoder.apkscan.scanresults

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cooperthecoder.apkscan.R
import com.cooperthecoder.apkscan.types.InstalledAppInfo
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.utils.EmptyViewHolder

class AppInfoSection(private val appInfo: InstalledAppInfo) : Section(
    SectionParameters.builder()
        .headerResourceId(R.layout.app_info_header)
        .itemResourceId(R.layout.app_info_section)
        .build()
) {

    class AppInfoHolder(view: View) : RecyclerView.ViewHolder(view) {
        val packageNameTextView: TextView = view.findViewById(R.id.packageName)
        val packageIcon: ImageView = view.findViewById(R.id.appIcon)
        val appName: TextView = view.findViewById(R.id.appName)
    }

    override fun getContentItemsTotal(): Int {
        // This section simply displays the app's package name, icon and version.
        return 1
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as AppInfoHolder
        vh.packageNameTextView.text = appInfo.packageName
        vh.packageIcon.setImageDrawable(appInfo.icon)
        vh.appName.text = appInfo.appName
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return AppInfoHolder(view)
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return EmptyViewHolder(view)
    }
}