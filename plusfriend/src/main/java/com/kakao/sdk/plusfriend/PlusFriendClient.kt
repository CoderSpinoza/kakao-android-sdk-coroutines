package com.kakao.sdk.plusfriend

import android.content.Context
import android.net.Uri

/**
 * @author kevin.kang. Created on 22/02/2019..
 */
interface PlusFriendClient {
    fun addFriendUrl(plusFriendId: String): Uri
    fun chatUrl(plusFriendId: String): Uri
    fun openAddFriend(context: Context, plusFriendId: String)
    fun openChat(context: Context, plusFriendId: String)

    companion object {
        val instance: PlusFriendClient by lazy { DefaultPlusFriendClient() }
    }
}