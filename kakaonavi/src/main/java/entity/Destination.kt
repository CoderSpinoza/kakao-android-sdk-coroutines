package com.kakao.sdk.kakaonavi.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.common.Exclude
import com.kakao.sdk.kakaonavi.Constants

/**
 * @author kevin.kang. Created on 18/02/2019..
 */
class Destination(
        @Exclude override val name: String,
        @Exclude override val x: Number,
        @Exclude override val y: Number,
        @Exclude @SerializedName(Constants.RP_FLAG) override val rpFlag: String? = null,
        @Exclude override val cid: String? = null,
        val guideId: Int? = null
) : Location(name, x, y, rpFlag, cid)