package com.cooperthecoder.apkscan

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_scan_results.*

class ScanResultsActivity : AppCompatActivity() {

    companion object {
        const val PACKAGE_NAME = "package_name"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_results)
        val packageName = intent.getStringExtra(PACKAGE_NAME)

        Flowable.fromCallable {
            val pm = applicationContext.packageManager
            val flags = PackageManager.GET_SHARED_LIBRARY_FILES or PackageManager.GET_META_DATA
            return@fromCallable pm.getApplicationInfo(packageName, flags)
        }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { applicationInfo ->
                val sb = StringBuilder()
                applicationInfo.sharedLibraryFiles.forEach {
                    sb.append(it)
                }

                // TODO: Display more info from app
                val applicationInfoData = "Package name: ${applicationInfo.packageName}\n" +
                        "Data directory: ${applicationInfo.dataDir}\n" +
                        "Shared libraries: $sb"
                applicationInfoText.text = applicationInfoData
            }



    }
}