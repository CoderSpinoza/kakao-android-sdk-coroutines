package com.kakao.sdk.friends

import com.kakao.sdk.friends.data.FriendsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
interface FriendsApi {
    @GET("v1/friends")
    fun getFriends(@QueryMap friendsRequest: Map<String, String>): Observable<FriendsResponse>

    @GET("v1/friends/operation")
    fun friendsOperation(@QueryMap friendsOperationRequest: Map<String, String>): Observable<FriendsResponse>
}