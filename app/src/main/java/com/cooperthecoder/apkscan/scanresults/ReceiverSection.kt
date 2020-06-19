package com.cooperthecoder.apkscan.scanresults

import android.content.pm.ActivityInfo
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cooperthecoder.apkscan.R
import com.cooperthecoder.apkscan.utils.BinaryFlagUtils
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.utils.EmptyViewHolder

class ReceiverSection(private val receivers: Array<ActivityInfo>) : Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.receiver_item)
        .headerResourceId(R.layout.receiver_header)
        .build()
) {

    class ReceiverHolder(view: View) : RecyclerView.ViewHolder(view) {
        val receiverName: TextView = view.findViewById(R.id.receiverName)
        val permissionRequired: TextView = view.findViewById(R.id.permissionRequired)
        val exportedStatus: TextView = view.findViewById(R.id.exportedStatus)
        val enabledStatus: TextView = view.findViewById(R.id.enabledStatus)
        val flags: TextView = view.findViewById(R.id.enabledFlags)
    }

    override fun getContentItemsTotal(): Int {
        return receivers.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ReceiverHolder
        val receiverInfo = receivers[position]
        vh.receiverName.text = receiverInfo.name ?: receiverInfo.targetActivity
        if (receiverInfo.permission != null) {
            vh.permissionRequired.text = "Permission required: ${receiverInfo.permission}"
        } else {
            vh.permissionRequired.visibility = View.GONE
        }

        vh.exportedStatus.text = if (receiverInfo.exported) {
            "Exported"
        } else {
            "Not Exported"
        }
        vh.enabledStatus.text = if (receiverInfo.enabled) {
            "Enabled "
        } else {
            "Disabled"
        }


        val flags = BinaryFlagUtils.getEnabledFlags(receiverInfo)
        if (flags.isNotEmpty()) {
            val enabledFlagString = "Flags:\n${flags.joinToString("\n")}"
            vh.flags.text = enabledFlagString
        } else {
            vh.flags.visibility = View.GONE
        }
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return ReceiverHolder(view)
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return EmptyViewHolder(view)
    }
}