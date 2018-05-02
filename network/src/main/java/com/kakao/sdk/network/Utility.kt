package com.kakao.sdk.network

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import java.io.File
import java.net.URLDecoder
import java.security.MessageDigest
import java.util.*

/**
 * @author kevin.kang. Created on 2018. 3. 30..
 */
object Utility {
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
                Constants.SDK, BuildConfig.VERSION_NAME,
                Constants.OS, Build.VERSION.SDK_INT,
                Constants.LANG, Locale.getDefault().language.toLowerCase(), Locale.getDefault().country.toUpperCase(),
                Constants.ORIGIN, Utility.getKeyHash(context),
                Constants.DEVICE, Build.MODEL.replace("\\s".toRegex(), "-").toUpperCase(),
                Constants.ANDROID_PKG, context.packageName,
                Constants.APP_VER, context.packageManager.getPackageInfo(context.packageName, 0).versionName
        )
    }

    fun getMetadata(context: Context, key: String): String {
        val ai = context.packageManager.getApplicationInfo(
                context.packageName, PackageManager.GET_META_DATA)
        return ai.metaData.getString(key)
    }

    /**
     * Below methods are needed for tests.
     */
    fun parseQueryParams(queries: String?): Map<String, String> {
        if (queries == null) return mapOf()
        val kvList = queries.split("&").map { it.split("=") }.filter { it.size > 1 }.map { Pair(it[0], it[1]) }
        val map = mutableMapOf<String, String>()
        kvList.forEach { pair ->
            map[pair.first] = URLDecoder.decode(pair.second, "UTF-8")
        }
        return map
    }

    fun getJson(path: String): String {
        val uri = javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    fun hasAndNotNull(jsonObject: JsonObject, key: String): Boolean {
        return jsonObject.has(key) && jsonObject[key] !is JsonNull
    }
}