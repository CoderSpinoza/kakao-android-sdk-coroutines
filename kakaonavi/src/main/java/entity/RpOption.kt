package com.kakao.sdk.kakaonavi.entity

import com.kakao.sdk.common.IntEnum

/**
 * @author kevin.kang. Created on 18/02/2019..
 */
enum class RpOption(val option: Int) : IntEnum {
    /**
     * Fastest route
     */
    FAST(1), // 빠른길
    /**
     * Free route
     */
    FREE(2), // 무료도로
    /**
     * Shortest route
     */
    SHORTEST(3), // 최단거리
    /**
     * Exclude motorway
     */
    NO_AUTO(4), // 자동차전용제외
    /**
     * Wide road first
     */
    WIDE(5), // 큰길우선
    /**
     * Highway first
     */
    HIGHWAY(6), // 고속도로우선
    /**
     * Normal road first
     */
    NORMAL(8), // 일반도로우선
    /**
     * Recommended route (Current default option if not set)
     */
    RECOMMENDED(100); // 추천경로 (기본값)

    override fun getValue(): Int {
        return option
    }
}