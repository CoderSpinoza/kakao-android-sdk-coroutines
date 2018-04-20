package com.kakao.sdk.network

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.util.*

/**
 * @author kevin.kang. Created on 2018. 3. 30..
 */
class Utility {
    companion object {
        fun getKeyHash(context: Context): String? {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
            for (signature in packageInfo.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
            }
            return null
        }

        fun getKAHeader(context: Context): String {
            return String.format("%s/%s %s/android-%s %s/%s-%s %s/%s %s/%s %s/%s %s/%s",
                    StringSet.KA_KEY_SDK, BuildConfig.VERSION_NAME,
                    StringSet.KA_KEY_OS, Build.VERSION.SDK_INT,
                    StringSet.KA_KEY_LANG, Locale.getDefault().getLanguage().toLowerCase(), Locale.getDefault().country.toUpperCase(),
                    StringSet.KA_KEY_ORIGIN, Utility.getKeyHash(context),
                    StringSet.KA_KEY_DEVICE, Build.MODEL.replace("\\s".toRegex(), "-").toUpperCase(),
                    StringSet.KA_KEY_PACKAGE, context.packageName,
                    StringSet.KA_KEY_APP_VER, context.packageManager.getPackageInfo(context.packageName, 0).versionName
            )
        }

        fun getMetadata(context: Context, key: String): String {
            val ai = context.packageManager.getApplicationInfo(
                    context.packageName, PackageManager.GET_META_DATA)
            return ai.metaData.getString(key)
        }
    }
}