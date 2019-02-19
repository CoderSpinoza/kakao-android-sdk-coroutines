package com.kakao.sdk.kakaonavi.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.kakaonavi.Constants

/**
 * @author kevin.kang. Created on 18/02/2019..
 */
class KakaoNaviParams(
        @SerializedName(Constants.DESTINATION) val destination: Destination,
        @SerializedName(Constants.OPTION) val option: NaviOptions? = null,
        @SerializedName(Constants.VIA_LIST) val stops: List<Location>? = mutableListOf()
)