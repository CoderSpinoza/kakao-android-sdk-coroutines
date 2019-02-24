package com.kakao.sdk.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import java.io.File
import java.lang.NullPointerException
import java.net.URLDecoder
import java.security.MessageDigest
import java.util.Locale

/**
 * @author kevin.kang. Created on 2018. 3. 30..
 */
object Utility {
    fun getKeyHash(context: Context): String {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            val packageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNING_CERTIFICATES)
//            val signatures = packageInfo.signingInfo.signingCertificateHistory
//            for (signature in signatures) {
//                val md = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
//            }
//            throw RuntimeException()
//        } else {
//            return  getKeyHashDeprecated(context)
//        }
        return getKeyHashDeprecated(context)
    }

    @Suppress("DEPRECATION")
    @SuppressLint("PackageManagerGetSignatures")
    fun getKeyHashDeprecated(context: Context): String {
        val packageInfo = context.packageManager.getPackageInfo(
                context.packageName, PackageManager.GET_SIGNATURES)
        for (signature in packageInfo.signatures) {
            val md = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
        }
        throw RuntimeException()
    }

    fun getKAHeader(context: Context): String {
        return String.format("%s/%s %s/android-%s %s/%s-%s %s/%s %s/%s %s/%s %s/%s",
                Constants.SDK, BuildConfig.VERSION_NAME,
                Constants.OS, Build.VERSION.SDK_INT,
                Constants.LANG, Locale.getDefault().language.toLowerCase(),
                Locale.getDefault().country.toUpperCase(),
                Constants.ORIGIN, getKeyHash(context),
                Constants.DEVICE, Build.MODEL.replace("\\s".toRegex(), "-").toUpperCase(),
                Constants.ANDROID_PKG, context.packageName,
                Constants.APP_VER,
                context.packageManager.getPackageInfo(context.packageName, 0).versionName
        )
    }

    fun getExtras(context: Context): JsonObject {
        val jsonObject = JsonObject()
        jsonObject.addProperty(Constants.APP_PACKAGE, context.packageName)
        jsonObject.addProperty(Constants.APP_KEY_HASH, getKeyHash(context))
        jsonObject.addProperty(Constants.KA, getKAHeader(context))
        return jsonObject
//        try {
//            return JSONObject()
//                    .put(Constants.APP_PACKAGE, context.packageName)
//                    .put(Constants.APP_KEY_HASH, getKeyHash(context))
//                    .put(Constants.KA, getKAHeader(context))
//        } catch (e: JSONException) {
//            throw IllegalArgumentException("JSON parsing error while constructing extras string: $e")
//        }
    }

    fun getMetadata(context: Context, key: String): String? {
        val ai = context.packageManager.getApplicationInfo(
                context.packageName, PackageManager.GET_META_DATA)
        return ai.metaData.getString(key)
    }

    /**
     * Below methods are needed for tests.
     */
    fun parseQuery(queries: String?): Map<String, String> {
        if (queries == null) return mapOf()
        val kvList = queries.split("&")
                .map { it.split("=") }.filter { it.size > 1 }.map { Pair(it[0], it[1]) }
        val map = mutableMapOf<String, String>()
        kvList.forEach { pair ->
            map[pair.first] = URLDecoder.decode(pair.second, "UTF-8")
        }
        return map
    }

    fun buildQuery(params: Map<String, String>?): String {
        if (params == null || params.isEmpty()) return ""
        return params.map { (k, v) -> "$k=$v" }.reduce { acc, s -> "$acc&$s" }
    }

    fun getJson(path: String): String {
        val classLoader = javaClass.classLoader ?: throw NullPointerException()
        val uri = classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    fun hasAndNotNull(jsonObject: JsonObject, key: String): Boolean {
        return jsonObject.has(key) && jsonObject[key] !is JsonNull
    }
}