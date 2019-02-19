package com.kakao.sdk.kakaolink.entity

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.kakao.sdk.kakaolink.Constants

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
class KakaoLinkResponse(val templateId: String,
                        val templateArgs: JsonObject,
                        val templateMsg: JsonObject,
                        val warningMsg: JsonObject,
                        val argumentMsg: JsonObject) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}