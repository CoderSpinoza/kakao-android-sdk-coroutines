package com.kakao.sdk.kakaolink.entity

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
class KakaoLinkResponse(@SerializedName("template_id") val templateId: String,
                        @SerializedName("template_args") val templateArgs: JsonObject,
                        @SerializedName("template_msg") val templateMsg: JsonObject,
                        @SerializedName("warning_msg") val warningMsg: JsonObject,
                        @SerializedName("argument_msg") val argumentMsg: JsonObject) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}