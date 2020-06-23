package com.cooperthecoder.apkscan.scanresults

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cooperthecoder.apkscan.types.InstalledAppInfo
import com.cooperthecoder.apkscan.R
import com.google.android.material.tabs.TabLayoutMediator
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

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

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
                    appIcon.setImageDrawable(installedAppInfo.icon)
                    appName.text = installedAppInfo.appName
                    appPackageName.text = installedAppInfo.packageName
                    val pagerAdapter = ScanResultsAdapter(this, installedAppInfo)
                    scanResultsPager.adapter = pagerAdapter
                    val tabLayoutMediator =
                        TabLayoutMediator(scanResultsTabs, scanResultsPager) { tab, position ->
                            tab.text = pagerAdapter.sectionName(position)
                        }
                    tabLayoutMediator.attach()
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
