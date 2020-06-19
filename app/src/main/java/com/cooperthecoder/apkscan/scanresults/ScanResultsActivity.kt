package com.cooperthecoder.apkscan.scanresults

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cooperthecoder.apkscan.types.InstalledAppInfo
import com.cooperthecoder.apkscan.R
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_scan_results.*
import java.io.File

class ScanResultsActivity : AppCompatActivity() {

    companion object {
        const val PACKAGE_NAME = "package_name"

    }

    private val tag = javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_results)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        val sectionedAdapter = SectionedRecyclerViewAdapter()
        scanResultsList.adapter = sectionedAdapter
        scanResultsList.layoutManager = LinearLayoutManager(this)

        val packageName = intent.getStringExtra(PACKAGE_NAME)!!
        Flowable.fromCallable {
            val pm = applicationContext.packageManager
            val flags = PackageManager.GET_SHARED_LIBRARY_FILES or
                    PackageManager.GET_META_DATA or
                    PackageManager.GET_ACTIVITIES or
                    PackageManager.GET_RECEIVERS or
                    PackageManager.GET_PROVIDERS or
                    PackageManager.GET_PERMISSIONS or
                    PackageManager.GET_INTENT_FILTERS or
                    PackageManager.GET_SERVICES or
                    PackageManager.GET_URI_PERMISSION_PATTERNS or
                    PackageManager.GET_GIDS

            val packageInfo = pm.getPackageInfo(packageName, flags)
            val version = packageInfo.versionName ?: getString(R.string.no_version_provided)
            val publicFiles = File(packageInfo.applicationInfo.dataDir)
                .walk()
                .iterator()
                .asSequence()
                .toList()
                .filter { file ->
                    file.isFile
                }

            return@fromCallable InstalledAppInfo(
                appName = packageInfo.applicationInfo.loadLabel(pm).toString(),
                packageName = packageInfo.packageName,
                activities = getOrEmptyArray(packageInfo::activities),
                receivers = getOrEmptyArray(packageInfo::receivers),
                permissions = getOrEmptyArray(packageInfo::permissions),
                providers = getOrEmptyArray(packageInfo::providers),
                publicFiles = publicFiles,
                services = getOrEmptyArray(packageInfo::services),
                sharedLibraries = getOrEmptyArray(packageInfo.applicationInfo::sharedLibraryFiles),
                version = version,
                icon = packageInfo.applicationInfo.loadIcon(pm)
            )
        }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { installedAppInfo: InstalledAppInfo ->
                    Log.d(tag, installedAppInfo.toString())

                    // Partition RecyclerView by scan results area
                    sectionedAdapter.addSection(AppInfoSection(installedAppInfo))
                    if (installedAppInfo.activities.isNotEmpty()) {
                        sectionedAdapter.addSection(ActivitySection(installedAppInfo.activities))
                    }

                    if (installedAppInfo.services.isNotEmpty()) {
                        sectionedAdapter.addSection(ServiceSection(installedAppInfo.services))
                    }

                    if (installedAppInfo.receivers.isNotEmpty()) {
                        sectionedAdapter.addSection(ReceiverSection(installedAppInfo.receivers))
                    }

                    if (installedAppInfo.providers.isNotEmpty()) {
                        sectionedAdapter.addSection(ProviderSection(installedAppInfo.providers))
                    }

                    if (installedAppInfo.permissions.isNotEmpty()) {
                        sectionedAdapter.addSection(PermissionSection(installedAppInfo.permissions))
                    }

                    if (installedAppInfo.publicFiles.count() !=  0) {
                        sectionedAdapter.addSection(FileSection(installedAppInfo.publicFiles))
                    }

                    if (installedAppInfo.sharedLibraries.isNotEmpty()) {
                        sectionedAdapter.addSection(
                            SharedLibrarySection(installedAppInfo.sharedLibraries)
                        )
                    }

                    sectionedAdapter.notifyDataSetChanged()
                },
                { exception ->
                    // TODO: Handle errors caused by dead PackageManager
                    exception.printStackTrace()
                    AlertDialog.Builder(this)
                        .setMessage(exception.message)
                        .setCancelable(true)
                        .create()
                        .show()
                }

            )
    }

    private inline fun <reified T> getOrEmptyArray(method: () -> Array<T>?): Array<T> {
        return try {
            method() ?: arrayOf()
        } finally {
            arrayOf<T>()
        }
    }
}
