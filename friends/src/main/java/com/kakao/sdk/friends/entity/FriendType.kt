package com.kakao.sdk.friends.entity

/**
 * 가져올 친구 타입
 */
enum class FriendType(val enumName: String, val value: Int) {
    /**
     * 카카오톡 친구
     */
    KAKAO_TALK("talk", 0),
    /**
     * 카카오스토리 친구
     */
    KAKAO_STORY("story", 1),
    /**
     * 카카오톡과 카카오스토리 친구
     */
    KAKAO_TALK_AND_STORY("talkstory", 2);
}