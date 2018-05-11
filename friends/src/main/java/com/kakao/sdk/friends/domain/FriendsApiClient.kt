package com.kakao.sdk.friends.domain

import com.kakao.sdk.friends.data.DefaultFriendsApiClient
import com.kakao.sdk.friends.entity.*
import io.reactivex.Single

/**
 * @author kevin.kang. Created on 2018. 5. 10..
 */
interface FriendsApiClient {
    fun friends(friendType: FriendType? = null,
                friendFilter: FriendFilter? = null,
                friendOrder: FriendOrder? = null,
                secureResource: Boolean? = null,
                offset: Int? = null,
                limit: Int? = null,
                order: String? = null,
                url: String? = null): Single<FriendsResponse>

    fun friendsSet(firstId: String,
                   secondId: String,
                   operator: FriendsOperator,
                   secureResource: Boolean? = null,
                   offset: Int? = null,
                   limit: Int? = null,
                   order: String? = null,
                   url: String? = null): Single<FriendsResponse>

    companion object {
        val instance by lazy { DefaultFriendsApiClient() as FriendsApiClient }
    }
}