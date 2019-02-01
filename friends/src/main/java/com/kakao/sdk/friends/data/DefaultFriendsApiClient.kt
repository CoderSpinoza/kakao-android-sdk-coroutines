package com.kakao.sdk.friends.data

import com.kakao.sdk.friends.entity.*
import com.kakao.sdk.auth.data.ApiService
import com.kakao.sdk.auth.data.ApiErrorInterceptor
import io.reactivex.Single

/**
 * @author kevin.kang. Created on 2018. 3. 30..
 */
class DefaultFriendsApiClient(val api: FriendsApi = ApiService.kapi.create(FriendsApi::class.java),
                              private val apiErrorInterceptor: ApiErrorInterceptor = ApiErrorInterceptor.instance): FriendsApiClient {
    override fun friends(friendType: FriendType?,
                         friendFilter: FriendFilter?,
                         friendOrder: FriendOrder?,
                         secureResource: Boolean?,
                         offset: Int?,
                         limit: Int?,
                         order: String?,
                         url: String?): Single<FriendsResponse> {
        return api.friends(friendType, friendFilter, friendOrder, secureResource, offset, limit, order, url)
                .compose(apiErrorInterceptor.handleApiError())
    }
}