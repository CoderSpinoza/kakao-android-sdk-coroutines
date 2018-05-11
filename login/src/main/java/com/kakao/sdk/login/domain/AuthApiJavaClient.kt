package com.kakao.sdk.login.domain

import com.kakao.sdk.login.data.DefaultAuthApiJavaClient
import com.kakao.sdk.login.entity.token.AccessTokenResponse
import io.reactivex.Single

/**
 * This is an interface for Auth API which will be primarily used from java.
 *
 * @author kevin.kang. Created on 2018. 5. 9..
 */
interface AuthApiJavaClient {
    fun issueAccessToken(authCode: String): Single<AccessTokenResponse>
    fun refreshAccessToken(refreshToken: String): Single<AccessTokenResponse>

    companion object {
        val instance by lazy { DefaultAuthApiJavaClient(AuthApiClient.instance) as AuthApiJavaClient }
    }
}