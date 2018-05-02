package com.kakao.sdk.friends.entity

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
enum class FriendsOperator(val enumName: String, @Suppress("UNUSED_PARAMETER") value: Int) {
    UNDEFINED("undefined", -1),
    INTERSECTION("i", 0),
    UNION("u", 1),
    SUBTRACTION("s", 2);

    override fun toString(): String {
        return enumName
    }
}