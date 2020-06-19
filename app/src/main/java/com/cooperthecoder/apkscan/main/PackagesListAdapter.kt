package com.cooperthecoder.apkscan.main

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cooperthecoder.apkscan.R
import com.cooperthecoder.apkscan.scanresults.ScanResultsActivity
import com.cooperthecoder.apkscan.types.SortableApplicationInfo

class PackagesListAdapter(private val context: Context, packages: List<ApplicationInfo>) : RecyclerView.Adapter<PackagesListAdapter.Holder>() {

    private val className = javaClass.name

    private val installedPackages = SortableApplicationInfo.sortableList(packages).sorted()


    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val appIcon: ImageView = view.findViewById(R.id.appIcon)
        val appPackageName: TextView = view.findViewById(R.id.applicationPackageName)
        val appName: TextView = view.findViewById(R.id.applicationName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.package_list_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return installedPackages.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val pkg = installedPackages[position]
        Log.d(className,"Found package $pkg")
        holder.appIcon.setImageDrawable(pkg.loadIcon(context.packageManager))
        holder.appPackageName.text = pkg.packageName
        holder.appName.text = pkg.loadLabel(context.packageManager)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ScanResultsActivity::class.java)
            intent.putExtra(ScanResultsActivity.PACKAGE_NAME, pkg.packageName)
            context.startActivity(intent)
        }
    }

}