package com.kakao.sdk.message.template.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.message.template.Constants

data class ContentObject(@SerializedName(Constants.TITLE) val title: String,
                         @SerializedName(Constants.IMAGE_URL) val imageUrl: String,
                         @SerializedName(Constants.LINK) val link: LinkObject,
                         @SerializedName(Constants.IMAGE_WIDTH) val imageWidth: Int? = null,
                         @SerializedName(Constants.IMAGE_HEIGHT) val imageHeight: Int? = null)