package com.kakao.sdk.friends

import com.kakao.sdk.friends.entity.FriendFilter
import com.kakao.sdk.friends.entity.FriendOrder
import com.kakao.sdk.friends.entity.FriendType
import com.kakao.sdk.friends.entity.FriendsResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
interface FriendsApi {
    @GET(Constants.FRIENDS_V1_PATH)
    suspend fun friends(
            @Query(Constants.FRIEND_TYPE) friendType: FriendType? = null,
            @Query(Constants.FRIEND_FILTER) friendFilter: FriendFilter? = null,
            @Query(Constants.FRIEND_ORDER) friendOrder: FriendOrder? = null,
            @Query(com.kakao.sdk.auth.Constants.SECURE_RESOURCE) secureResource: Boolean? = null,
            @Query(Constants.OFFSET) offset: Int? = null,
            @Query(Constants.LIMIT) limit: Int? = null,
            @Query(Constants.ORDER) order: String? = null,
            @Query(Constants.URL) url: String? = null
    ): FriendsResponse
}