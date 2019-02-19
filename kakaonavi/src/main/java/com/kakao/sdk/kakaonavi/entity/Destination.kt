package com.kakao.sdk.kakaonavi.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.common.Exclude
import com.kakao.sdk.kakaonavi.Constants

/**
 * @author kevin.kang. Created on 18/02/2019..
 */
class Destination(@Exclude @SerializedName(Constants.NAME) override val name: String,
                  @Exclude @SerializedName(Constants.X) override val x: Number,
                  @Exclude @SerializedName(Constants.Y) override val y: Number,
                  @Exclude @SerializedName(Constants.RP_FLAG) override val rpFlag: String? = null,
                  @Exclude @SerializedName(Constants.CID) override val cid: String? = null,
                  @SerializedName(Constants.GUIDE_ID) val guideId: Int? = null
): Location(name, x, y, rpFlag, cid)