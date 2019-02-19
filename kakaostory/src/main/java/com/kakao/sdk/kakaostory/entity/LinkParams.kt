package com.kakao.sdk.kakaostory.entity

import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
data class LinkParams(@SerializedName("androidExecParams") val androidExecParams: Map<String, String>? = null,
                      @SerializedName("iosExecParams") val iosExecParams: Map<String, String>? = null,
                      @SerializedName("androidMarketParams") val androidMarketParams: Map<String, String>? = null,
                      @SerializedName("iosMarketParams") val iosMarketParams: Map<String, String>? = null)