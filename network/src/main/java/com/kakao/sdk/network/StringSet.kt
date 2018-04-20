package com.kakao.sdk.network

/**
 * @author kevin.kang. Created on 2018. 3. 30..
 */
class StringSet {
    companion object {
        const val META_APP_KEY = "com.kakao.sdk.AppKey"
        const val META_CLIENT_SECRET = "com.kakao.sdk.ClientSecret"

        const val HEADER_KEY_AUTH = "Authorization"
        const val HEADER_KEY_KA = "KA"

        const val KA_KEY_SDK = "sdk"
        const val KA_KEY_OS = "os"
        const val KA_KEY_LANG = "lang"
        const val KA_KEY_ORIGIN = "origin"
        const val KA_KEY_DEVICE = "device"
        const val KA_KEY_PACKAGE = "android_pkg"
        const val KA_KEY_APP_VER = "app_ver"
        const val HEADER_PREFIX_APP_KEY = "KakaoAK"
        const val HEADER_PREFIX_BEARER = "Bearer"
    }
}