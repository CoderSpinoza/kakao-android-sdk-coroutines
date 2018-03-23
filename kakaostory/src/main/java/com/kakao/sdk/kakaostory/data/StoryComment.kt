package com.kakao.sdk.kakaostory.data

import com.google.gson.GsonBuilder

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
data class StoryComment(val writer: StoryActor,
                        val text: String) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}