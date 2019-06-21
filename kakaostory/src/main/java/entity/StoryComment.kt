package com.kakao.sdk.kakaostory.entity

import com.kakao.sdk.common.KakaoGsonFactory

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
data class StoryComment(val writer: StoryActor, val text: String) {
    override fun toString(): String {
        return KakaoGsonFactory.pretty.toJson(this)
    }
}