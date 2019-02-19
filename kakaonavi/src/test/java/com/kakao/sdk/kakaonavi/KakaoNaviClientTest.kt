package com.kakao.sdk.kakaonavi

import android.content.Context
import android.content.Intent
import android.content.pm.*
import androidx.test.core.app.ApplicationProvider
import com.kakao.sdk.common.ApplicationInfo
import com.kakao.sdk.common.Utility
import com.kakao.sdk.kakaonavi.entity.*
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowPackageManager

/**
 * @author kevin.kang. Created on 18/02/2019..
 */
@RunWith(RobolectricTestRunner::class)
class KakaoNaviClientTest {
    lateinit var context: Context
    lateinit var packageManager: PackageManager
    lateinit var shadow: ShadowPackageManager

    lateinit var client: DefaultKakaoNaviClient

    @Before fun setup() {
        context = ApplicationProvider.getApplicationContext<Context>()
        packageManager = context.packageManager
        shadow = Shadows.shadowOf(packageManager)
        client = DefaultKakaoNaviClient(ApplicationInfo("client_id", "individual", "key_hash", "client_secret"))
    }
    @Test fun isKakaoNaviInstalled() {
        val intent = Intent(Intent.ACTION_MAIN).setPackage(Constants.NAVI_PACKAGE)
                .addCategory(Intent.CATEGORY_LAUNCHER)

        val activityInfo = ActivityInfo()
        activityInfo.packageName = Constants.NAVI_PACKAGE
        activityInfo.name = "SplashActivity" // 임의의 이름
        val resolveInfo = ResolveInfo()
        resolveInfo.activityInfo = activityInfo

        assertFalse(client.isKakaoNaviInstalled(context))
        shadow.addResolveInfoForIntent(intent, resolveInfo)
        assertTrue(client.isKakaoNaviInstalled(context))
    }

    @Test fun shareDestinationUri() {
        val info = PackageInfo()
        info.packageName = context.packageName
        info.versionName = "1.0.0"
        info.signatures = arrayOf(Signature("00000000"))
        shadow.installPackage(info)

        val uri = client.shareDestinationUri(context, KakaoNaviParams(
                Destination("name", 30, 30),
                NaviOptions(CoordType.WGS84, vehicleType = VehicleType.SECOND, rpOption = RpOption.FREE)
        ))
        System.out.println(uri.getQueryParameter(Constants.EXTRAS))
        System.out.println(uri.getQueryParameter(Constants.PARAM))
    }
}