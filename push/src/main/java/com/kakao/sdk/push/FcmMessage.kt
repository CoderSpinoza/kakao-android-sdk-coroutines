package com.kakao.sdk.push

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
data class FcmMessage( val customField: Map<String, Any>? = null,
                      val notification: Map<String, Any>? = null,
                      val returnUrl: String? = null,
                      val timeToLive: Long ?= null,
                      val dryRun: Boolean? = null,
                      val priority: String? = null,
                      val collapse: String? = null)