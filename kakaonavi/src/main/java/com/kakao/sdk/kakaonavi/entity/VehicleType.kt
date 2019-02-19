package com.kakao.sdk.kakaonavi.entity

import com.kakao.sdk.common.IntEnum

/**
 * @author kevin.kang. Created on 18/02/2019..
 */
enum class VehicleType(val intValue: Int): IntEnum {
    /**
     * 1종 (승용차/소형승합차/소형화물화)
     */
    FIRST(1),
    /**
     *  2종 (중형승합차/중형화물차)
     */
    SECOND(2),
    /**
     * 3종 (대형승합차/2축 대형화물차)
     */
    THIRD(3),
    /**
     * 4종 (3축 대형화물차)
     */
    FOURTH(4),
    /**
     * 5종 (4축이상 특수화물차)
     */
    FIFTH(5),
    /**
     * 6종 (경차)
     */
    SIXTH(6),
    /**
     * 이륜차
     */
    TWO_WHEEL(7);

    override fun getValue(): Int {
        return intValue
    }
}