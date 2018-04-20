package com.kakao.sdk.friends.entity

enum class FriendType(val enumName: String, val value: Int) {
    KAKAO_TALK("talk", 0),
    KAKAO_STORY("story", 1),
    KAKAO_TALK_AND_STORY("talkstory", 2);
}