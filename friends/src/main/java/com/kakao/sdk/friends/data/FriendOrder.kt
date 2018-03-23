package com.kakao.sdk.friends.data

enum class FriendOrder(val enumName: String, value: Int) {
    UNDEFINED("undefined", -1),
    NICKNAME("nickname", 0),
    LAST_CHAT_TIME("last_chat_time", 1),
    TALK_CREATED_AT("talk_created_at", 2),
    AGE("age", 3),
    AFFINITY("affinity", 4);
}