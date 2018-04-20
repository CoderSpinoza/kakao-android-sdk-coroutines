package com.kakao.sdk.kakaostory.entity

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
data class LinkParams(val androidExecParams: Map<String, String>? = null,
                      val iosExecParams: Map<String, String>? = null,
                      val androidMarketParams: Map<String, String>? = null,
                      val iosMarketParams: Map<String, String>? = null)