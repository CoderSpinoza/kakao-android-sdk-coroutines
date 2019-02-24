package com.kakao.sdk.message.template

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.message.template.entity.ButtonObject
import com.kakao.sdk.message.template.entity.CommerceObject
import com.kakao.sdk.message.template.entity.ContentObject

data class CommerceTemplate(
    @SerializedName(Constants.CONTENT) val content: ContentObject,
    @SerializedName(Constants.COMMERCE) val commerce: CommerceObject,
    @SerializedName(Constants.BUTTONS) val buttons: List<ButtonObject>? = mutableListOf()
) : DefaultTemplate {
    @SerializedName(Constants.OBJECT_TYPE) val objectType = Constants.TYPE_COMMERCE
}