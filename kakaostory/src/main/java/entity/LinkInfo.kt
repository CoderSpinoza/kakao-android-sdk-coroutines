package com.kakao.sdk.kakaostory.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.common.KakaoGsonFactory
import com.kakao.sdk.kakaostory.Constants

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
data class LinkInfo(
        val url: String,
        val requestedUrl: String,
        val host: String,
        val title: String?,
        val description: String?,
        val section: String?,
        val type: String?,
        @SerializedName(Constants.IMAGE) val images: List<String>?
) {
    override fun toString(): String {
        return KakaoGsonFactory.pretty.toJson(this)
    }
}