package com.kakao.sdk.ageauth.entity

import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 4. 25..
 */
enum class AgeAuthLevel(val value: String) {
    @SerializedName("AUTH_LEVEL1")
    SELF("AUTH_LEVEL1"),
    @SerializedName("AUTH_LEVEL2")
    AGE("AUTH_LEVEL2")
}