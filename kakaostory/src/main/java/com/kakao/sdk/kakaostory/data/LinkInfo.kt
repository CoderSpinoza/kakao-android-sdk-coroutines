package com.kakao.sdk.kakaostory.data

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
data class LinkInfo(@SerializedName(Constants.URL) val url: String?,
                    @SerializedName(Constants.REQUESTED_URL) val requestedUrl: String?,
                    @SerializedName(Constants.HOST) val host: String?,
                    @SerializedName(Constants.TITLE) val title: String?,
                    @SerializedName(Constants.DESCRIPTION) val description: String?,
                    @SerializedName(Constants.SECTION) val section: String?,
                    @SerializedName(Constants.TYPE) val type: String?,
                    @SerializedName(Constants.IMAGE) val images: List<String>?) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}