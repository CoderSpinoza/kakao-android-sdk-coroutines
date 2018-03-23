package com.kakao.sdk.friends.data

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
data class FriendsRequest(val friendType: FriendType? = null,
                          val friendFilter: FriendFilter? = null,
                          val friendOrder: FriendOrder? = null,
                          val secureResource: Boolean? = null,
                          val offset: Int? = null,
                          val limit: Int? = null,
                          val order: String? = null,
                          val url: String? = null) {

    fun toMap(): Map<String, String> {
        val map  = HashMap<String, String>()
        friendType?.let { map.put("friend_type", friendType.enumName) }
        friendFilter?.let { map.put("friend_filter", friendFilter.enumName) }
        friendOrder?.let { map.put("friend_order", friendOrder.enumName) }
        secureResource?.let { map.put("secure_resource", secureResource.toString()) }
        offset?.let { map.put("offset", offset.toString()) }
        limit?.let { map.put("limit", limit.toString()) }
        order?.let { map.put("order", order) }
        url?.let { map.put("url", url) }
        return map
    }
}