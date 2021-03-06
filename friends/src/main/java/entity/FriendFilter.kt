package com.kakao.sdk.friends.entity

enum class FriendFilter(val enumName: String, val value: Int) {
    NONE("none", 0),
    REGISTERED("registered", 1),
    INVITABLE("invitable", 2);
}