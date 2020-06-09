package com.cooperthecoder.apkscan

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pm = applicationContext.packageManager

        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        val adapter = PackagesListAdapter(this)
        packagesList.adapter = adapter
        packagesList.layoutManager = LinearLayoutManager(this)
        adapter.addPackages(packages)
    }
}