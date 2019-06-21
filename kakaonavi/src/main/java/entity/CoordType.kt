package com.kakao.sdk.kakaonavi.entity

import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 18/02/2019..
 */
enum class CoordType(val type: String) {
    /**
     * The World Geodetic System 84
     */
    @SerializedName("wgs84")
    WGS84("wgs84"),

    /**
     * Katech coordinate system
     */
    @SerializedName("katec")
    KATEC("katec");
}