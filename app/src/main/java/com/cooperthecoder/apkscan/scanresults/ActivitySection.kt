package com.cooperthecoder.apkscan.scanresults

import android.content.Intent
import android.content.pm.ActivityInfo
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cooperthecoder.apkscan.R
import com.cooperthecoder.apkscan.interact.InteractActivity
import com.cooperthecoder.apkscan.types.ComponentVisibility
import com.cooperthecoder.apkscan.utils.BinaryFlagUtils
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters

class ActivitySection(private val activities: Array<ActivityInfo>) : Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.activity_item)
        .build()
) {

    class ActivityHolder(view: View) : RecyclerView.ViewHolder(view) {
        val activityName: TextView = view.findViewById(R.id.activityName)
        val permissionRequired: TextView = view.findViewById(R.id.permissionRequired)
        val exportedStatus: TextView = view.findViewById(R.id.exportedStatus)
        val enabledStatus: TextView = view.findViewById(R.id.enabledStatus)
        val flags: TextView = view.findViewById(R.id.enabledFlags)
        val interactButton: Button = view.findViewById(R.id.interactButton)
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
            "⚠️ ${ComponentVisibility.PUBLIC}"
        } else {
            "✅ ${ComponentVisibility.PRIVATE}"
        }
        vh.enabledStatus.text = if (activityInfo.enabled) {
            "Enabled"
        } else {
            "Disabled"
        }

        val flags = BinaryFlagUtils.getEnabledFlags(activityInfo)
        if (flags.isNotEmpty()) {
            val enabledFlagString = "Flags:\n${flags.joinToString("\n")}"
            vh.flags.text = enabledFlagString
        } else {
            vh.flags.visibility = View.GONE
        }

        if (activityInfo.exported && activityInfo.permission == null) {
            vh.interactButton.setOnClickListener { view ->
                val context = view.context
                val intent = Intent(context, InteractActivity::class.java)
                    .putExtra(InteractActivity.COMPONENT_TYPE, InteractActivity.TYPE_ACTIVITY)
                    .putExtra(InteractActivity.PACKAGE_NAME, activityInfo.packageName)
                    .putExtra(InteractActivity.CLASS_NAME, activityInfo.name)

                context.startActivity(intent)
            }
        } else {
            vh.interactButton.visibility = View.GONE
        }
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return ActivityHolder(view)
    }
}