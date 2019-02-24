package com.kakao.sdk.kakaonavi.entity

/**
 * @author kevin.kang. Created on 18/02/2019..
 */
class KakaoNaviParams(
    val destination: Destination,
    val option: NaviOptions? = null,
    val viaList: List<Location>? = mutableListOf()
)