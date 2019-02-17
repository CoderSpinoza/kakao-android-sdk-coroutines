package com.kakao.sdk.message.template.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.message.template.Constants

data class ButtonObject(@SerializedName(Constants.TITLE) val title: String,
                        @SerializedName(Constants.LINK) val link: LinkObject)