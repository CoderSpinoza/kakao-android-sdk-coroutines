package com.kakao.sdk.common

import android.content.Context
import android.content.Intent
import android.content.pm.*
import android.net.Uri
import androidx.browser.customtabs.CustomTabsService
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowPackageManager

/**
 * @author kevin.kang. Created on 26/02/2019..
 */
@RunWith(RobolectricTestRunner::class)
class CustomTabsCommonClientTest {
    lateinit var context: Context
    lateinit var shadow: ShadowPackageManager

    @Before fun setup() {
        context = ApplicationProvider.getApplicationContext<Context>()
        shadow = Shadows.shadowOf(context.packageManager)
    }

    /**
     * 1. 디폴트 브라우저가 없다.
     * 2. CustomTabs 프로토콜을 지원하는 브라우저가 아예 미설치.
     */
    @Test fun resolveWhenNothingInstalled() {
        val testUri = Uri.parse("https://developers.kakao.com")
        val selected = CustomTabsCommonClient.resolveCustomTabsPackage(context,
                testUri)
        assertNull(selected)
    }

    /**
     * 1. 크롬이 디폴트 브라우저로 설정되어 있다.
     * 2. 크롬이 CustomTabs 프로토콜을 지원한다.
     */
    @Test fun resolveWhenOnlyChromeInstalled() {
        val testUri = Uri.parse("https://developers.kakao.com")
        installPackageWithCustomTabsSupport(shadow, "com.android.chrome", testUri, true)
        val selected = CustomTabsCommonClient.resolveCustomTabsPackage(context,
                testUri)
        assertEquals("com.android.chrome", selected)
    }

    /**
     * 1. 디폴트 브라우저가 없다.
     * 2. CustomTabs 프로토콜을 지원하는 앱이 두개 설치되어 있다.
     */
    @Test fun resolveWhenNoDefault() {
        val testUri = Uri.parse("https://developers.kakao.com")
        installPackageForUri(shadow, "android", testUri) // android ResolverActivity
        installPackageWithCustomTabsSupport(shadow, "com.samsung.browser", testUri, false)
        installPackageWithCustomTabsSupport(shadow, "com.android.chrome", testUri, false)
        val selected = CustomTabsCommonClient.resolveCustomTabsPackage(context,
                testUri)
        assertEquals("com.android.chrome", selected)
    }

    /**
     * 1. 디폴트 브라우저가 삼성 브라우저이다.
     * 2. CustomTabs 프로토콜을 지원하는 크롬과 삼성 브라우저가 설치되어 있다.
     */
    @Test fun resolveWhenThirdPartyIsDefault() {
        val testUri = Uri.parse("https://developers.kakao.com")
        installPackageWithCustomTabsSupport(shadow, "com.android.chrome", testUri)
        installPackageWithCustomTabsSupport(shadow, "com.samsung.browser", testUri, true)

        val selected = CustomTabsCommonClient.resolveCustomTabsPackage(context,
                testUri)
        assertEquals("com.samsung.browser", selected)
    }


    private fun installPackageWithCustomTabsSupport(
            packageManager: ShadowPackageManager,
            packageName: String,
            uri: Uri = Uri.parse("https://developers.kakao.com"),
            isDefault: Boolean = false
    ) {
        if (isDefault) {
            installPackageForUri(packageManager, packageName, uri)
        }
        installCustomTabsService(packageManager, packageName)
    }

    private fun installPackageForUri(packageManager: ShadowPackageManager, packageName: String, uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        val resolveInfo = ResolveInfo()
        val activityInfo = ActivityInfo()
        activityInfo.packageName = packageName
        activityInfo.name = "CustomTabsBrowserActivity"
        resolveInfo.activityInfo = activityInfo
        packageManager.addResolveInfoForIntent(intent, resolveInfo)
    }


    private fun installCustomTabsService(packageManager: ShadowPackageManager, packageName: String) {
        val serviceIntent = Intent(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION)
        val serviceResolveInfo = ResolveInfo()
        val serviceInfo = ServiceInfo()
        serviceInfo.packageName = packageName
        serviceInfo.name = "CustomTabsBrowserService"
        serviceResolveInfo.serviceInfo = serviceInfo
        packageManager.addResolveInfoForIntentNoDefaults(serviceIntent, serviceResolveInfo)
    }

}