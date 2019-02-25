package com.kakao.sdk.plusfriend

import android.net.Uri

/**
 * @author kevin.kang. Created on 22/02/2019..
 */
interface PlusFriendClient {
    fun addFriendUrl(plusFriendId: String): Uri
    fun chatUrl(plusFriendId: String): Uri
    fun openAddFriend(plusFriendId: String)
    fun openChat(plusFriendId: String)

    companion object {
        val instance: PlusFriendClient by lazy { DefaultPlusFriendClient() }
    }
}