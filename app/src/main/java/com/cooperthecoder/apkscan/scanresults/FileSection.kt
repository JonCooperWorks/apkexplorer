package com.cooperthecoder.apkscan.scanresults

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cooperthecoder.apkscan.R
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import java.io.File

class FileSection(private val publicFiles: Array<File>) : Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.file_item)
        .build()
) {
    class FilesHolder(view: View) : RecyclerView.ViewHolder(view) {
        val filenameText: TextView = view.findViewById(R.id.filenameText)
    }

    override fun getContentItemsTotal(): Int {
        return publicFiles.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val file = publicFiles[position]
        val vh = holder as FilesHolder
        vh.filenameText.text = file.absolutePath
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return FilesHolder(view)
    }
}