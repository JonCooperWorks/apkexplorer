package com.cooperthecoder.apkscan.scanresults

import android.content.pm.PermissionInfo
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cooperthecoder.apkscan.R
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.utils.EmptyViewHolder

class PermissionSection(private val permissions: Array<PermissionInfo>) : Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.permission_item)
        .build()
) {

    class PermissionHolder(view: View) : RecyclerView.ViewHolder(view) {
        val permissionName: TextView = view.findViewById(R.id.permissionName)
    }

    override fun getContentItemsTotal(): Int {
        return permissions.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as PermissionHolder
        val permissionInfo = permissions[position]
        vh.permissionName.text = permissionInfo.name
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return PermissionHolder(view)
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return EmptyViewHolder(view)
    }
}