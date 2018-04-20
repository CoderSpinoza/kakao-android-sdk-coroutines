package com.kakao.sdk.friends

import com.kakao.sdk.friends.entity.*
import com.kakao.sdk.login.ApiService
import io.reactivex.Observable

/**
 * @author kevin.kang. Created on 2018. 3. 30..
 */
class FriendsApiClient(val api: FriendsApi = ApiService.kapi.create(FriendsApi::class.java)) {
    fun getFriends(friendType: FriendType? = null,
                   friendFilter: FriendFilter? = null,
                   friendOrder: FriendOrder? = null,
                   secureResource: Boolean? = null,
                   offset: Int? = null,
                   limit: Int? = null,
                   order: String? = null,
                   url: String? = null): Observable<FriendsResponse> {
        return api.getFriends(friendType, friendFilter, friendOrder, secureResource, offset, limit, order, url)
    }

    fun friendsSet(firstId: String,
                   secondId: String,
                   operator: FriendsOperator,
                   secureResource: Boolean? = null,
                   offset: Int? = null,
                   limit: Int? = null,
                   order: String? = null,
                   url: String? = null): Observable<FriendsResponse> {
        return api.friendsOperation(firstId, secondId, operator, secureResource, offset, limit, order, url)
    }

    companion object {
        val instance = FriendsApiClient()
    }
}