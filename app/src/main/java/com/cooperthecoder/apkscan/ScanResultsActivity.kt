package com.cooperthecoder.apkscan

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_scan_results.*

class ScanResultsActivity : AppCompatActivity() {

    companion object {
        const val PACKAGE_NAME = "package_name"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_results)
        val packageName = intent.getStringExtra(PACKAGE_NAME)

        // TODO: Do this in background using RxAndroid
        val pm = applicationContext.packageManager
        val flags = PackageManager.GET_SHARED_LIBRARY_FILES or PackageManager.GET_META_DATA
        val applicationInfo = pm.getApplicationInfo(packageName, flags)
        val sb = StringBuilder()
        applicationInfo.sharedLibraryFiles.forEach {
            sb.append(it)
        }

        // TODO: Display app info in user-friendly way
        val applicationInfoData = "Package name: ${applicationInfo.packageName}\n" +
                "Data directory: ${applicationInfo.dataDir}\n" +
                "Shared libraries: $sb"

        applicationInfoText.text = applicationInfoData
    }
}