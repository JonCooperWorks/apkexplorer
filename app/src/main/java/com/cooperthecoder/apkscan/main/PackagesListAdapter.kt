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

class PackagesListAdapter(private val context: Context) : RecyclerView.Adapter<PackagesListAdapter.Holder>() {

    private val className = javaClass.name

    private val installedPackages = ArrayList<ApplicationInfo>()

    class Holder(view: View) : RecyclerView.ViewHolder(view) {

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
        holder.itemView.findViewById<ImageView>(R.id.appIcon).setImageDrawable(pkg.loadIcon(context.packageManager))
        holder.itemView.findViewById<TextView>(R.id.applicationPackageName).text = pkg.packageName
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ScanResultsActivity::class.java)
            intent.putExtra(ScanResultsActivity.PACKAGE_NAME, pkg.packageName)
            context.startActivity(intent)
        }
    }

    fun addPackages(packages: List<ApplicationInfo>) {
        installedPackages.clear()
        installedPackages.addAll(packages)
        notifyDataSetChanged()
    }
}