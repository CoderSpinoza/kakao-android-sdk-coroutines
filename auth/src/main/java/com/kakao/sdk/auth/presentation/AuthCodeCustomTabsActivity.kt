package com.kakao.sdk.auth.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService
import com.kakao.sdk.auth.Constants
import com.kakao.sdk.network.Utility
import java.util.*

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 3. 24..
 */
class AuthCodeCustomTabsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openChromeCustomTab()
    }

    fun openChromeCustomTab() {
        val continueUri = Uri.Builder().scheme(com.kakao.sdk.network.Constants.SCHEME).authority(com.kakao.sdk.network.Constants.KAUTH)
                .path(Constants.AUTHORIZE_PATH)
                .appendQueryParameter(Constants.CLIENT_ID, Utility.getMetadata(this, com.kakao.sdk.network.Constants.META_APP_KEY))
                .appendQueryParameter(Constants.REDIRECT_URI, String.format("kakao%s://oauth", Utility.getMetadata(this, com.kakao.sdk.network.Constants.META_APP_KEY)))
                .appendQueryParameter(Constants.RESPONSE_TYPE, Constants.CODE)
                .appendQueryParameter(Constants.APPROVAL_TYPE, "individual")
                .build()
        CustomTabsIntent.Builder().enableUrlBarHiding().setShowTitle(true).build().launchUrl(this, continueUri)
//        val packageName = resolveCustomTabsPackageName(this, continueUri)
//        CustomTabsClient.bindCustomTabsService(this, packageName, object : CustomTabsServiceConnection() {
//            override fun onCustomTabsServiceConnected(name: ComponentName, client: CustomTabsClient) {
//                val builder = CustomTabsIntent.Builder()
//                builder.enableUrlBarHiding()
//                builder.setShowTitle(true)
//                val customTabsIntent = builder.build()
//                customTabsIntent.intent.data = continueUri
//                customTabsIntent.intent.`package` = packageName
//                startActivity(customTabsIntent.intent)
//            }
//
//            override fun onServiceDisconnected(name: ComponentName) {}
//        })
    }

    internal fun resolveCustomTabsPackageName(context: Context, uri: Uri): String? {
        var packageName: String? = null
        var availableChrome: String? = null

        // get ResolveInfo for default browser
        val browserIntent = Intent(Intent.ACTION_VIEW, uri)
        val resolveInfo = context.packageManager.resolveActivity(browserIntent, PackageManager.MATCH_DEFAULT_ONLY)

        // get ResolveInfos for browsers that support custom tabs protocol
        val serviceIntent = Intent()
        serviceIntent.action = CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION
        val serviceInfos = context.packageManager.queryIntentServices(serviceIntent, 0)
        for (info in serviceInfos) {
            // check if chrome is available on this device
            if (availableChrome == null && isPackageNameChrome(info.serviceInfo.packageName)) {
                availableChrome = info.serviceInfo.packageName
            }
            // check if the browser being looped is the default browser
            if (info.serviceInfo.packageName == resolveInfo.activityInfo.packageName) {
                packageName = resolveInfo.activityInfo.packageName
                break
            }
        }

        // if the default browser does not support custom tabs protocol, use chrome if available.
        if (packageName == null && availableChrome != null) {
            packageName = availableChrome
        }
        Log.d("selected browser", packageName)
        return packageName
    }

    internal fun isPackageNameChrome(packageName: String): Boolean {
        return chromePackageNames.contains(packageName)
    }

    private val chromePackageNames = Arrays.asList(
            "com.android.chrome",
            "com.chrome.beta",
            "com.chrome.dev")
}