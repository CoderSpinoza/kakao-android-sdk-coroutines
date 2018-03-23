package com.kakao.sdk.login.data

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
data class AccessTokenRequest(val clientId: String,
                              val redirectUri: String,
                              val approvalType: String = "individual",
                              val androidKeyHash: String,
                              val clientSecret: String? = null,
                              val authCode: String? = null,
                              val refreshToken: String? = null,
                              val grantType: String = "authorization_code") {
    fun toMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map.put("client_id", clientId)
        map.put("redirect_uri", redirectUri)
        map.put("approval_type", approvalType)
        map.put("android_key_hash", androidKeyHash)
        map.put("grant_type", grantType)
        authCode?.let { map.put("code", it) }
        refreshToken?.let { map.put("refresh_token", it) }
        clientSecret?.let { map.put("client_secret", it) }
        return map
    }
}