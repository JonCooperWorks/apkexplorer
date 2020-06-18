package com.cooperthecoder.apkscan.scanresults

import android.content.pm.ServiceInfo
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cooperthecoder.apkscan.R
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.utils.EmptyViewHolder

class ServiceSection(private val services: Array<ServiceInfo>) : Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.service_item)
        .headerResourceId(R.layout.service_header)
        .build()
) {
    class ServiceHolder(view: View) : RecyclerView.ViewHolder(view) {
        val serviceName: TextView = view.findViewById(R.id.serviceName)
        val permissionRequired: TextView = view.findViewById(R.id.permissionRequired)
        val exportedStatus: TextView = view.findViewById(R.id.exportedStatus)
        val enabledStatus: TextView = view.findViewById(R.id.enabledStatus)
    }

    override fun getContentItemsTotal(): Int {
        return services.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ServiceHolder
        val serviceInfo = services[position]
        vh.serviceName.text = serviceInfo.name
        if (serviceInfo.permission != null) {
            vh.permissionRequired.text = "Permission required: ${serviceInfo.permission}"
        } else {
            vh.permissionRequired.visibility = View.GONE
        }

        vh.exportedStatus.text = if (serviceInfo.exported) {
            "Exported"
        } else {
            "Not Exported"
        }
        vh.enabledStatus.text = if (serviceInfo.enabled) {
            "Enabled "
        } else {
            "Disabled"
        }
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return ServiceHolder(view)
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return EmptyViewHolder(view)
    }
}