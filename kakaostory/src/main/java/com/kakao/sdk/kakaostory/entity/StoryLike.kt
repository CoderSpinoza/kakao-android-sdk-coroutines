package com.kakao.sdk.kakaostory.entity

import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
data class StoryLike(val actor: StoryActor,
                     val emotion: Emotion) {
    enum class Emotion(val papiEmotion: String) {
        /**
         * 좋아요
         */
        @SerializedName("LIKE")
        LIKE("LIKE"),
        /**
         * 멋져요
         */
        @SerializedName("COOL")
        COOL("COOL"),
        /**
         * 기뻐요
         */
        @SerializedName("HAPPY")
        HAPPY("HAPPY"),
        /**
         * 슬퍼요
         */
        @SerializedName("SAD")
        SAD("SAD"),
        /**
         * 힘내요
         */
        @SerializedName("CHEER_UP")
        CHEER_UP("CHEER_UP"),
        /**
         * 정의되지 않은 느낌
         */
        @SerializedName("NOT_DEFINED")
        NOT_DEFINED("NOT_DEFINED");
    }
}