package com.kakao.sdk.friends.entity

/**
 * 친구 리스트 정렬 기준.
 * @author kevin.kang
 */
enum class FriendOrder(val enumName: String, value: Int) {
    UNDEFINED("undefined", -1),
    /**
     * 이름 순 정렬
     */
    NICKNAME("nickname", 0),
    /**
     * 마지막 채팅시간 순 정렬
     */
    LAST_CHAT_TIME("last_chat_time", 1),
    /**
     * 카카오톡 userId 생성시간 기준 정렬
     */
    TALK_CREATED_AT("talk_created_at", 2),
    /**
     * 나이 순 정렬
     */
    AGE("age", 3),
    /**
     * 친밀도 순 정렬
     */
    AFFINITY("affinity", 4);
}