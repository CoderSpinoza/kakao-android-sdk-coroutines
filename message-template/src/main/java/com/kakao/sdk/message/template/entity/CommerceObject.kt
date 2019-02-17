package com.kakao.sdk.message.template.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.message.template.Constants

data class CommerceObject(@SerializedName(Constants.REGULAR_PRICE) val regularPrice: Int,
                          @SerializedName(Constants.DISCOUNT_PRICE) val discountPrice: Int? = null,
                          @SerializedName(Constants.FIXED_DISCOUNT_PRICE) val fixedDiscountPrice: Int? = null,
                          @SerializedName(Constants.DISCOUNT_RATE) val discountRate: Int? = null)