package com.kakao.sdk.message.template

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.message.template.entity.ButtonObject
import com.kakao.sdk.message.template.entity.LinkObject

data class TextTemplate(
    @SerializedName(Constants.TEXT) val text: String,
    @SerializedName(Constants.LINK) val link: LinkObject,
    @SerializedName(Constants.BUTTON_TITLE) val buttonTitle: String,
    @SerializedName(Constants.BUTTONS) val buttons: List<ButtonObject> = mutableListOf()
) : DefaultTemplate {
    @SerializedName(Constants.OBJECT_TYPE) val objectType = Constants.TYPE_TEXT
}