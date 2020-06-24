package com.cooperthecoder.apkscan.scanresults

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cooperthecoder.apkscan.R
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters

class AppInfoSection(private val flags: List<String>) : Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.app_info)
        .build()
) {

    class AppInfoHolder(view: View) : RecyclerView.ViewHolder(view) {
        val flagName: TextView = view.findViewById(R.id.flag)
    }

    override fun getContentItemsTotal(): Int {
        return flags.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as AppInfoHolder
        vh.flagName.text = flags[position]
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return AppInfoHolder(view)
    }
}