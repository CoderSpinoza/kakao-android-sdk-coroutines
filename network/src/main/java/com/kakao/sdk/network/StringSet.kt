package com.kakao.sdk.network

/**
 * @author kevin.kang. Created on 2018. 3. 30..
 */
class StringSet {
    companion object {
        val META_APP_KEY = "com.kakao.sdk.AppKey"
        val META_CLIENT_SECRET = "com.kakao.sdk.ClientSecret"

        val HEADER_KEY_AUTH = "Authorization"
        val HEADER_KEY_KA = "KA"

        val KA_KEY_SDK = "sdk"
        val KA_KEY_OS = "os"
        val KA_KEY_LANG = "lang"
        val KA_KEY_ORIGIN = "origin"
        val KA_KEY_DEVICE = "device"
        val KA_KEY_PACKAGE = "android_pkg"
        val KA_KEY_APP_VER = "app_ver"
        val HEADER_PREFIX_APP_KEY = "KakaoAK"
        val HEADER_PREFIX_BEARER = "Bearer"
    }
}