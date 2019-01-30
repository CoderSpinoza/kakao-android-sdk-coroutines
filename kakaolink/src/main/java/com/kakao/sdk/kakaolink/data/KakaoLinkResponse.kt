package com.kakao.sdk.kakaolink.data

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
class KakaoLinkResponse(@SerializedName(Constants.TEMPLATE_ID) val templateId: String,
                        @SerializedName(Constants.TEMPLATE_ARGS) val templateArgs: JsonObject,
                        @SerializedName(Constants.TEMPLATE_MSG) val templateMsg: JsonObject,
                        @SerializedName(Constants.WARNING_MSG) val warningMsg: JsonObject,
                        @SerializedName(Constants.ARGUMENT_MSG) val argumentMsg: JsonObject) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}