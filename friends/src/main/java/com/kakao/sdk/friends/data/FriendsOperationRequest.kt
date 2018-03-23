package com.kakao.sdk.friends.data

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
data class FriendsOperationRequest(private val firstId: String,
                                   private val secondId: String,
                                   private val operator: FriendsOperator,
                                   val secureResource: Boolean? = null,
                                   val offset: Int? = null,
                                   val limit: Int? = null,
                                   val order: String? = null,
                                   val url: String? = null) {
    fun toMap(): Map<String, String> {
        val map = HashMap<String, String>()
        map.put("first_id", firstId)
        map.put("second_id", secondId)
        map.put("operator", operator.enumName)
        secureResource?.let { map.put("secure_resource", it.toString()) }
        offset?.let { map.put("offset", it.toString()) }
        limit?.let { map.put("limit", it.toString()) }
        order?.let { map.put("order", it) }
        url?.let { map.put("url", it) }
        return map
    }
}