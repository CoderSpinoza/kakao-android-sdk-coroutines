package com.kakao.sdk.message.template.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.message.template.Constants

data class LinkObject(@SerializedName(Constants.WEB_URL) val webUrl: String? = null,
                      @SerializedName(Constants.MOBILE_WEB_URL) val mobileWebUrl: String? = null,
                      @SerializedName(Constants.ANDROID_PARAMS) val androidExecParams: String? = null,
                      @SerializedName(Constants.IOS_PARAMS) val iosExecParams: String? = null)