package com.cooperthecoder.apkscan.scanresults

import android.content.pm.ActivityInfo
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cooperthecoder.apkscan.R
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.utils.EmptyViewHolder

class ActivitySection(private val activities: Array<ActivityInfo>) : Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.activity_item)
        .headerResourceId(R.layout.activity_header)
        .build()
) {

    class ActivityHolder(view: View) : RecyclerView.ViewHolder(view) {
        val activityName: TextView = view.findViewById(R.id.activityName)
        val permissionRequired: TextView = view.findViewById(R.id.permissionRequired)
        val exportedStatus: TextView = view.findViewById(R.id.exportedStatus)
        val enabledStatus: TextView = view.findViewById(R.id.enabledStatus)
    }

    override fun getContentItemsTotal(): Int {
        return activities.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ActivityHolder
        val activityInfo = activities[position]
        vh.activityName.text = activityInfo.name ?: activityInfo.targetActivity
        if (activityInfo.permission != null) {
            vh.permissionRequired.text = "Permission required: ${activityInfo.permission}"
        } else {
            vh.permissionRequired.visibility = View.GONE
        }

        vh.exportedStatus.text = if (activityInfo.exported) {
            "Exported"
        } else {
            "Not Exported"
        }
        vh.enabledStatus.text = if (activityInfo.enabled) {
            "Enabled "
        } else {
            "Disabled"
        }

    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return ActivityHolder(view)
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return EmptyViewHolder(view)
    }

}