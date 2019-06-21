package com.kakao.sdk.kakaostory.entity

import com.kakao.sdk.common.KakaoGsonFactory

/**
 * @author kevin.kang. Created on 2018. 3. 21..
 */
data class StoryPostResponse(val id: String) {
    override fun toString(): String {
        return KakaoGsonFactory.pretty.toJson(this)
    }
}