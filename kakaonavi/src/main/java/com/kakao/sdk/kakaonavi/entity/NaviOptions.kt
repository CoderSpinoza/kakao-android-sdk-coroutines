package com.kakao.sdk.kakaonavi.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.kakaonavi.Constants

/**
 * @author kevin.kang. Created on 18/02/2019..
 */
class NaviOptions(
        val coordType: CoordType? = null,
        val vehicleType: VehicleType? = null,
        @SerializedName(Constants.RP_OPTION) val rpOption: RpOption? = null,
        val routeInfo: Boolean? = null,
        @SerializedName(Constants.S_X) val startX: Double? = null,
        @SerializedName(Constants.S_Y) val startY: Double? = null,
        @SerializedName(Constants.S_ANGLE) val startAngle: Int? = null,
        val userId: String? = null,
        val returnUri: String? = null
)