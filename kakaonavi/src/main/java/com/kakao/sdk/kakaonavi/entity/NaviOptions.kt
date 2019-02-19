package com.kakao.sdk.kakaonavi.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.kakaonavi.Constants

/**
 * @author kevin.kang. Created on 18/02/2019..
 */
class NaviOptions(
        @SerializedName(Constants.COORD_TYPE) val coordType: CoordType? = null,
        @SerializedName(Constants.VEHICLE_TYPE) val vehicleType: VehicleType? = null,
        @SerializedName(Constants.RP_OPTION) val rpOption: RpOption? = null,
        @SerializedName(Constants.ROUTE_INFO) val routeInfo: Boolean? = null,
        @SerializedName(Constants.S_X) val startX: Double? = null,
        @SerializedName(Constants.S_Y) val startY: Double? = null,
        @SerializedName(Constants.S_ANGLE) val startAngle: Int? = null,
        @SerializedName(Constants.USER_ID) val userId: String? = null,
        @SerializedName(Constants.RETURN_URI) val returnUri: String? = null
)