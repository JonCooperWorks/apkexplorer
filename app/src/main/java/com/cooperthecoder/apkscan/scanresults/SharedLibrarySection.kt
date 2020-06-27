package com.cooperthecoder.apkscan.scanresults

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cooperthecoder.apkscan.R
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters

class SharedLibrarySection(private val sharedLibraries: Array<String>) : Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.shared_library_item)
        .build()
) {

    class SharedLibrariesHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sharedLibraryName: TextView = view.findViewById(R.id.sharedLibraryName)
    }

    override fun getContentItemsTotal(): Int {
        return sharedLibraries.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as SharedLibrariesHolder
        val sharedLibrary = sharedLibraries[position]
        vh.sharedLibraryName.text = sharedLibrary
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return SharedLibrariesHolder(view)
    }
}