package com.kakao.sdk.message.template

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.message.template.entity.ButtonObject
import com.kakao.sdk.message.template.entity.ContentObject
import com.kakao.sdk.message.template.entity.LinkObject

data class ListTemplate(@SerializedName(Constants.HEADER_TITLE) val headerTitle: String,
                        @SerializedName(Constants.HEADER_LINK) val headerLink: LinkObject,
                        @SerializedName(Constants.CONTENTS) val contents: List<ContentObject>? = mutableListOf(),
                        @SerializedName(Constants.BUTTONS) val buttons: List<ButtonObject>? = mutableListOf()): DefaultTemplate {
    @SerializedName(Constants.OBJECT_TYPE) val objectType = Constants.TYPE_LIST
}