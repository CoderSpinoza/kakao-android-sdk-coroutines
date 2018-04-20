package com.kakao.sdk.s2.entity

/**
 * @author kevin.kang. Created on 2018. 3. 27..
 */
data class S2Event(val timestamp: Long,
                   val from: String,
                   val to: String,
                   val action: String,
                   val properties: Map<String, String>)