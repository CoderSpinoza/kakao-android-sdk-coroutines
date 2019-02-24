package com.kakao.sdk.message.template.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.message.template.Constants

/**
 * 가격 정보를 표현하기 위해 사용되는 오브젝트.
 *
 * @param regularPrice 정상가격
 * @param discountPrice 할인된 가격
 * @param discountRate 할인율
 * @param fixedDiscountPrice 정액 할인 가격
 *
 * @author kevin.kang.
 */
data class CommerceObject(
    @SerializedName(Constants.REGULAR_PRICE) val regularPrice: Int,
    @SerializedName(Constants.DISCOUNT_PRICE) val discountPrice: Int? = null,
    @SerializedName(Constants.FIXED_DISCOUNT_PRICE) val fixedDiscountPrice: Int? = null,
    @SerializedName(Constants.DISCOUNT_RATE) val discountRate: Int? = null
)