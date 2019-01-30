package com.kakao.sdk.kakaostory.data

import com.google.gson.GsonBuilder

/**
 * @author kevin.kang. Created on 2018. 3. 21..
 */
data class StoryPostResponse(val id: String) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}