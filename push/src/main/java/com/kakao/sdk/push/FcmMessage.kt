package com.kakao.sdk.push

import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
data class FcmMessage(@SerializedName("custom_field") val customField: Map<String, Any>? = null,
                      val notification: Map<String, Any>? = null,
                      @SerializedName("return_url") val returnUrl: String? = null,
                      @SerializedName("time_to_live") val timeToLive: Long ?= null,
                      @SerializedName("dry_run") val dryRun: Boolean? = null,
                      val priority: String? = null,
                      val collapse: String? = null)