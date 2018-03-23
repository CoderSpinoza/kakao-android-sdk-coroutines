package com.kakao.sdk.friends

import com.kakao.sdk.friends.data.*
import com.kakao.sdk.login.ApiService
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.robolectric.shadows.ShadowLog

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
class FriendsApiTest {
    private lateinit var api: FriendsApi

    @Before
    fun setup() {
        ShadowLog.stream = System.out
        api = ApiService.kapi.create(FriendsApi::class.java)
    }
    @Test
    fun getFriends() {
        val request = FriendsRequest(offset = 0, limit = 100, friendType = FriendType.KAKAO_TALK_AND_STORY, friendOrder = FriendOrder.AGE, secureResource = true, order = "desc")

        Observable.just(request).map { req -> req.toMap() }
                .flatMap { requestMap -> api.getFriends(requestMap) }
                .map { friendsResponse -> FriendsOperationRequest(friendsResponse.resultId, friendsResponse.resultId, FriendsOperator.UNION, secureResource = true, offset = 0, limit = 100, order = "desc") }
                .flatMap { operationRequest -> api.friendsOperation(operationRequest.toMap()) }
                .subscribe({ response ->
                    ShadowLog.e("friends", response.toString())
                }, { error ->
                    ShadowLog.e("error??", error.toString())
                })
    }
}