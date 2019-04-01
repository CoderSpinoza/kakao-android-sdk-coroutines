package com.kakao.sdk.common

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsService
import androidx.browser.customtabs.CustomTabsServiceConnection

import androidx.browser.customtabs.CustomTabsIntent
import java.lang.UnsupportedOperationException

/**
 * @author kevin.kang. Created on 26/02/2019..
 */
object CustomTabsCommonClient {

    @Throws(UnsupportedOperationException::class)
    fun openWithDefault(context: Context, uri: Uri): ServiceConnection? {
        val packageName = resolveCustomTabsPackage(context, uri) ?: throw UnsupportedOperationException()
        Log.d("CustomTabsCommonClient", "Choosing ${packageName} as custom tabs browser")
        val connection = object : CustomTabsServiceConnection() {
            override fun onCustomTabsServiceConnected(name: ComponentName?, client: CustomTabsClient?) {
                val builder = CustomTabsIntent.Builder()
                        .enableUrlBarHiding().setShowTitle(true)
                val customTabsIntent = builder.build()
                customTabsIntent.intent.data = uri
                customTabsIntent.intent.setPackage(packageName)
                context.startActivity(customTabsIntent.intent)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.d("CustomTabsCommonClient", "onServiceDisconnected: $name")
            }
        }
        val bound = CustomTabsClient.bindCustomTabsService(context, packageName, connection)
        return if (bound) connection else null
    }

    fun open(context: Context, uri: Uri) {
        CustomTabsIntent.Builder().enableUrlBarHiding().setShowTitle(true).build()
                .launchUrl(context, uri)
    }

    fun resolveCustomTabsPackage(context: Context, uri: Uri): String? {
        var packageName: String? = null
        var chromePackage: String? = null
        // get ResolveInfo for default browser
        val intent = Intent(Intent.ACTION_VIEW, uri)
        val resolveInfo = context.packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        val serviceIntent = Intent().setAction(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION)
        val serviceInfos = context.packageManager.queryIntentServices(serviceIntent, 0)
        for (info in serviceInfos) {
            // check if chrome is available on this device
            if (chromePackage == null && isPackageNameChrome(info.serviceInfo.packageName)) {
                chromePackage = info.serviceInfo.packageName
            }
            // check if the browser being looped is the default browser
            if (info.serviceInfo.packageName == resolveInfo.activityInfo.packageName) {
                packageName = resolveInfo.activityInfo.packageName
                break
            }
        }
        if (packageName == null && chromePackage != null) {
            packageName = chromePackage
        }
        return packageName
    }

    private fun isPackageNameChrome(packageName: String): Boolean {
        return chromePackageNames.contains(packageName)
    }

    private val chromePackageNames = arrayOf(
            "com.android.chrome",
            "com.chrome.beta",
            "com.chrome.dev")
}