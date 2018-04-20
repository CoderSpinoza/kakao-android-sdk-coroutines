package com.kakao.sdk.kakaostory.entity

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
data class LinkInfo(val url: String,
                    @SerializedName("requested_url") val requestedUrl: String,
                    val host: String,
                    val title: String,
                    val description: String,
                    val section: String,
                    val type: String,
                    @SerializedName("image") val images: List<String>) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}