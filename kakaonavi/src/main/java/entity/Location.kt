package com.kakao.sdk.kakaonavi.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.kakaonavi.Constants

/**
 * @author kevin.kang. Created on 18/02/2019..
 */
open class Location(
        open val name: String,
        open val x: Number,
        open val y: Number,
        @SerializedName(Constants.RP_FLAG) open val rpFlag: String? = null,
        open val cid: String? = null
)