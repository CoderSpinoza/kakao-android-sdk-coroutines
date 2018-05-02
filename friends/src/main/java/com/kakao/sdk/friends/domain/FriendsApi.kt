package com.kakao.sdk.friends.domain

import com.kakao.sdk.friends.entity.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
interface FriendsApi {
    @GET("v1/friends")
    fun friends(@Query("friend_type") friendType: FriendType? = null,
                @Query("friend_filter") friendFilter: FriendFilter? = null,
                @Query("friend_order") friendOrder: FriendOrder? = null,
                @Query("secure_resource") secureResource: Boolean? = null,
                @Query("offset") offset: Int? = null,
                @Query("limit") limit: Int? = null,
                @Query("order") order: String? = null,
                @Query("url") url: String? = null): Single<FriendsResponse>

    @GET("v1/friends/operation")
    fun friendsOperation(@Query("first_id") firstId: String,
                         @Query("second_id") secondId: String,
                         @Query("operator") operator: FriendsOperator,
                         @Query("secure_resource") secureResource: Boolean? = null,
                         @Query("offset") offset: Int? = null,
                         @Query("limit") limit: Int? = null,
                         @Query("order") order: String? = null,
                         @Query("url") url: String? = null): Single<FriendsResponse>
}