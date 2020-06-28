package com.cooperthecoder.apkscan.interact

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageItemInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cooperthecoder.apkscan.R
import kotlinx.android.synthetic.main.activity_interact.*
import kotlinx.android.synthetic.main.activity_interact.toolbar
import kotlinx.android.synthetic.main.activity_scan_results.*
import kotlinx.android.synthetic.main.package_list_item.*

class InteractActivity : AppCompatActivity() {

    companion object {
        const val COMPONENT_TYPE = "component_type"
        const val CLASS_NAME = "class_name"
        const val PACKAGE_NAME = "package_name"

        const val TYPE_ACTIVITY = "activity"
        const val TYPE_SERVICE = "service"
        const val TYPE_RECEIVER = "receiver"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interact)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        val componentType = intent.getStringExtra(COMPONENT_TYPE)!!
        val className = intent.getStringExtra(CLASS_NAME)!!
        val packageName = intent.getStringExtra(PACKAGE_NAME)!!

        val componentName = ComponentName(packageName, className)
        val componentInfo: PackageItemInfo = when (componentType) {
            TYPE_ACTIVITY -> {
                packageManager.getActivityInfo(componentName, PackageManager.GET_META_DATA)
            }

            TYPE_RECEIVER -> {
                packageManager.getReceiverInfo(componentName, PackageManager.GET_META_DATA)
            }

            TYPE_SERVICE -> {
                packageManager.getServiceInfo(componentName, PackageManager.GET_META_DATA)
            }

            else -> {
                throw IllegalArgumentException("Illegal component type $componentType")
            }
        }


        icon.setImageDrawable(componentInfo.loadIcon(packageManager))
        appTitle.text = componentInfo.loadLabel(packageManager)
        appTitlePackageName.text = packageName

        sendIntentButton.setOnClickListener {
            val intentToSend = Intent().apply {
                component = ComponentName(componentInfo.packageName, componentInfo.name)
                val dataUri = dataUriField.text.toString()
                if (dataUri != "") {
                    data = Uri.parse(dataUri)
                }
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            handleInteractWithComponent(componentType, intentToSend)
        }
    }

    private fun handleInteractWithComponent(componentType: String, intentToSend: Intent) {
        val packageInteraction = PackageInteraction(this)
        when (componentType) {
            TYPE_ACTIVITY -> {
                packageInteraction.startActivity(intentToSend)
            }

            TYPE_SERVICE -> {
                packageInteraction.startService(intentToSend)
            }

            TYPE_RECEIVER -> {
                packageInteraction.sendBroadcast(intentToSend)
            }

            else -> {
                throw IllegalArgumentException("Invalid component type $componentType")
            }
        }

        Toast.makeText(this, "Intent sent", Toast.LENGTH_LONG).show()

    }
}