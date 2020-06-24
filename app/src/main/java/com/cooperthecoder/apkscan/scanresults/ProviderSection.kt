package com.cooperthecoder.apkscan.scanresults

import android.content.pm.ProviderInfo
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cooperthecoder.apkscan.R
import com.cooperthecoder.apkscan.utils.BinaryFlagUtils
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.utils.EmptyViewHolder

class ProviderSection(private val providers: Array<ProviderInfo>) : Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.provider_item)
        .build()
) {

    class ProviderHolder(view: View) : RecyclerView.ViewHolder(view) {
        val providerName: TextView = view.findViewById(R.id.providerName)
        val exportedStatus: TextView = view.findViewById(R.id.exportedStatus)
        val enabledStatus: TextView = view.findViewById(R.id.enabledStatus)
        val multiprocessStatus: TextView = view.findViewById(R.id.multiProcessStatus)
        val forceUriPermissionStatus: TextView = view.findViewById(R.id.forceUriPermissionStatus)
        val grantUriPermissionStatus: TextView = view.findViewById(R.id.grantUriPermissionsStatus)
        val readPermission: TextView = view.findViewById(R.id.readPermission)
        val writePermission: TextView = view.findViewById(R.id.writePermission)
        val pathPermissions: TextView = view.findViewById(R.id.pathPermissions)
        val flags: TextView = view.findViewById(R.id.enabledFlags)
    }

    override fun getContentItemsTotal(): Int {
        return providers.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ProviderHolder
        val providerInfo = providers[position]
        vh.providerName.text = providerInfo.name
        vh.exportedStatus.text = if (providerInfo.exported) {
            "Exported"
        } else {
            "Not Exported"
        }
        vh.enabledStatus.text = if (providerInfo.enabled) {
            "Enabled "
        } else {
            "Disabled"
        }
        vh.grantUriPermissionStatus.text = if (providerInfo.grantUriPermissions) {
            "Grant URI Permissions Enabled"
        } else {
            "Grant URI Permissions Disabled"
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vh.forceUriPermissionStatus.text = if (providerInfo.forceUriPermissions) {
                "Force URI Permissions Enabled"
            } else {
                "Force URI Permissions Disabled"
            }
        } else {
            vh.forceUriPermissionStatus.visibility = View.GONE
        }

        vh.multiprocessStatus.text = if (providerInfo.multiprocess) {
            "Multi-process"
        } else {
            "Single Process"
        }

        if (providerInfo.readPermission ?: "" != "") {
            vh.readPermission.text = providerInfo.readPermission
        } else {
            vh.readPermission.visibility = View.GONE
        }

        if (providerInfo.writePermission ?: "" != "") {
            vh.writePermission.text = providerInfo.writePermission
        } else {
            vh.writePermission.visibility = View.GONE
        }

        val pathPermissions = providerInfo.pathPermissions?.joinToString(", ")
        if (pathPermissions ?: "" != "") {
            vh.pathPermissions.text = providerInfo.pathPermissions!!.joinToString(" ,")
        } else {
            vh.writePermission.visibility = View.GONE
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vh.forceUriPermissionStatus.text = if (providerInfo.forceUriPermissions) {
                "Force URI Permissions: enabled"
            } else {
                "Force URI Permissions: disabled"
            }
        } else {
            vh.forceUriPermissionStatus.visibility = View.GONE
        }

        vh.grantUriPermissionStatus.text = if (providerInfo.grantUriPermissions) {
            "Grant URI Permissions: enabled"
        } else {
            "Grant URI Permissions: disabled"
        }

        val flags = BinaryFlagUtils.getEnabledFlags(providerInfo)
        if (flags.isNotEmpty()) {
            val enabledFlagString = "Flags:\n${flags.joinToString("\n")}"
            vh.flags.text = enabledFlagString
        } else {
            vh.flags.visibility = View.GONE
        }
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return ProviderHolder(view)
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return EmptyViewHolder(view)
    }
}