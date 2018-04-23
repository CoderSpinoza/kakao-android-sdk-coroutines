package com.kakao.sdk.friends

import com.kakao.sdk.friends.domain.FriendsApi
import com.kakao.sdk.friends.entity.*
import com.kakao.sdk.login.data.ApiService
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
        api.getFriends(offset =  0, limit = 100, friendOrder = FriendOrder.NICKNAME, secureResource = true, order = "desc")
                .flatMap { friends -> api.friendsOperation(friends.resultId, friends.resultId, FriendsOperator.UNION, secureResource = true, offset = 0, limit = 100, order = "desc") }
                .subscribe({ response ->
                    ShadowLog.e("friends", response.toString())
                }, { error ->
                    ShadowLog.e("error??", error.toString())
                })
    }
}