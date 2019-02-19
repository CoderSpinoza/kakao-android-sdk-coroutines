package com.kakao.sdk.kakaonavi.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.kakaonavi.Constants

/**
 * @author kevin.kang. Created on 18/02/2019..
 */
open class Location(@SerializedName(Constants.NAME) open val name: String,
                    @SerializedName(Constants.X) open val x: Number,
                    @SerializedName(Constants.Y) open val y: Number,
                    @SerializedName(Constants.RP_FLAG) open val rpFlag: String? = null,
                    @SerializedName(Constants.CID) open val cid: String? = null)