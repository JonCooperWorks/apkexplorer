package com.cooperthecoder.apkscan.main

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cooperthecoder.apkscan.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = PackagesListAdapter(this)
        packagesList.adapter = adapter
        packagesList.layoutManager = LinearLayoutManager(this)

        Flowable.fromCallable {
            val pm = applicationContext.packageManager
            return@fromCallable pm.getInstalledApplications(PackageManager.GET_META_DATA)
        }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { packages ->
                adapter.addPackages(packages)
            }
    }
}