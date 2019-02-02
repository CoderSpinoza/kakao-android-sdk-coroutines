package com.kakao.sdk.auth.data

import com.kakao.sdk.auth.AccessTokenRepo
import com.kakao.sdk.auth.AuthApi
import com.kakao.sdk.auth.DefaultAuthApiClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class DefaultAuthApiClientTest {

    private lateinit var authApiClient: DefaultAuthApiClient
    private lateinit var accessTokenRepo: AccessTokenRepo
    private lateinit var authApi: AuthApi

    @BeforeEach fun setup() {
        accessTokenRepo = Mockito.spy(AccessTokenRepo::class.java)
        authApi = Mockito.spy(AuthApi::class.java)
        authApiClient = DefaultAuthApiClient(authApi, accessTokenRepo)
    }

    @Nested
    @DisplayName("Issuing access token")
    inner class IssueAccessToken {
        @Test fun with200Response() {

        }
    }

    @Nested
    @DisplayName("Refreshing access token")
    inner class RefreshAccessToken {}

}