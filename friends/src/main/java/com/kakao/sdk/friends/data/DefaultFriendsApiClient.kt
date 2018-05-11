package com.kakao.sdk.friends.data

import com.kakao.sdk.friends.domain.FriendsApi
import com.kakao.sdk.friends.domain.FriendsApiClient
import com.kakao.sdk.friends.entity.*
import com.kakao.sdk.login.data.ApiService
import com.kakao.sdk.login.data.ApiErrorInterceptor
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

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
                .subscribeOn(Schedulers.io())
    }

    override fun friendsSet(firstId: String,
                            secondId: String,
                            operator: FriendsOperator,
                            secureResource: Boolean?,
                            offset: Int?,
                            limit: Int?,
                            order: String?,
                            url: String?): Single<FriendsResponse> {
        return api.friendsOperation(firstId, secondId, operator, secureResource, offset, limit, order, url)
                .compose(apiErrorInterceptor.handleApiError())
                .subscribeOn(Schedulers.io())
    }
}