package com.kakao.sdk.auth

import android.net.Uri

object TestUriUtility {
    fun successfulRedirectUri(): Uri {
        return Uri.Builder().scheme("kakao123456").authority("oauth")
                .appendQueryParameter(Constants.CODE, "authorization_code").build()
    }

    fun failedRedirectUri(): Uri {
        return Uri.Builder().scheme("kakao123456").authority("oauth")
                .appendQueryParameter(Constants.ERROR, "invalid_grant")
                .appendQueryParameter(Constants.ERROR_DESCRIPTION, "error_description")
                .build()
    }
}