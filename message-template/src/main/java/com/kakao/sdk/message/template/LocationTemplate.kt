package com.kakao.sdk.message.template

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.message.template.entity.ButtonObject
import com.kakao.sdk.message.template.entity.ContentObject
import com.kakao.sdk.message.template.entity.SocialObject

data class LocationTemplate(@SerializedName(Constants.ADDRESS) val  address: String,
                            @SerializedName(Constants.CONTENT) val content: ContentObject,
                            @SerializedName(Constants.ADDRESS_TITLE) val addressTitle: String? = null,
                            @SerializedName(Constants.SOCIAL) val social: SocialObject? = null,
                            @SerializedName(Constants.BUTTONS) val buttons: MutableList<ButtonObject> = mutableListOf()
) {
    @SerializedName(Constants.OBJECT_TYPE) val objectType = Constants.TYPE_LOCATION
}