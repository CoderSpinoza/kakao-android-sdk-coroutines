package com.kakao.sdk.friends

import com.kakao.sdk.auth.network.OAuthApiFactory
import com.kakao.sdk.auth.network.ApiErrorInterceptor
import com.kakao.sdk.friends.entity.FriendFilter
import com.kakao.sdk.friends.entity.FriendOrder
import com.kakao.sdk.friends.entity.FriendType
import com.kakao.sdk.friends.entity.FriendsResponse

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 3. 30..
 */
class DefaultFriendsApiClient(
    val api: FriendsApi = OAuthApiFactory.kapi.create(FriendsApi::class.java),
    private val apiErrorInterceptor: ApiErrorInterceptor = ApiErrorInterceptor.instance
) : FriendsApiClient {
    override suspend fun friends(
        friendType: FriendType?,
        friendFilter: FriendFilter?,
        friendOrder: FriendOrder?,
        secureResource: Boolean?,
        offset: Int?,
        limit: Int?,
        order: String?,
        url: String?
    ): FriendsResponse {
        return apiErrorInterceptor.handleApiError {
            api.friends(friendType, friendFilter, friendOrder, secureResource, offset, limit,
                    order, url)
        }
    }
}