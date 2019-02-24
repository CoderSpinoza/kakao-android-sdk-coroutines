package com.kakao.sdk.friends

import com.kakao.sdk.friends.entity.FriendsResponse
import com.kakao.sdk.friends.entity.*

/**
 * @author kevin.kang. Created on 2018. 5. 10..
 */
interface FriendsApiClient {
    suspend fun friends(friendType: FriendType? = null,
                friendFilter: FriendFilter? = null,
                friendOrder: FriendOrder? = null,
                secureResource: Boolean? = null,
                offset: Int? = null,
                limit: Int? = null,
                order: String? = null,
                url: String? = null): FriendsResponse
    companion object {
        val instance by lazy { DefaultFriendsApiClient() as FriendsApiClient }
    }
}