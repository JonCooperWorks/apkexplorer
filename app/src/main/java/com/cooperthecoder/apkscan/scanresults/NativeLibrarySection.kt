package com.cooperthecoder.apkscan.scanresults

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cooperthecoder.apkscan.R
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters

class NativeLibrarySection(private val nativeLibraryFileNames: Array<String>): Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.native_lib_item)
        .build()
) {

    class NativeLibraryHolder(view: View): RecyclerView.ViewHolder(view) {
        val filename: TextView = view.findViewById(R.id.nativeLibPath)
    }

    override fun getContentItemsTotal(): Int {
        return nativeLibraryFileNames.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as NativeLibraryHolder
        vh.filename.text = nativeLibraryFileNames[position]
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return NativeLibraryHolder(view)
    }
}