package com.kakao.sdk.user.entity

import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2019-06-21..
 */
enum class Gender {
    @SerializedName("female")
    FEMALE,
    @SerializedName("male")
    MALE,
    @SerializedName("other")
    OTHER
}