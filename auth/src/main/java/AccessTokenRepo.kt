package com.kakao.sdk.auth

import com.kakao.sdk.auth.model.AccessToken
import com.kakao.sdk.auth.model.AccessTokenResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

/**
 * 카카오 API 에 사용되는 액세스 토큰, 리프레시 토큰을 관리하는 저장소.
 *
 * Repository for access and refresh token used for Kakao API.
 *
 * @since 2.0.0
 *
 * @author kevin.kang. Created on 2018. 3. 24..
 */
interface AccessTokenRepo {
    /**
     * 캐시에 저장되어 있는 [AccessToken] 객체를 리턴.
     *
     * @return AccessToken instance stored in the cache
     */
    fun fromCache(): AccessToken

    /**
     * OAuth Token API 를 통하여 받은 [AccessTokenResponse] 객체를 사용하여 캐시를 업데이트 함.
     *
     * @param accessTokenResponse [AccessTokenResponse] from [AuthApiClient.issueAccessToken] API
     * @return updated [AccessToken] instance
     */
    fun toCache(accessTokenResponse: AccessTokenResponse): AccessToken

    /**
     * 캐시에 저장되어 있는 [AccessToken] 객체를 지움.
     * Clear [AccessToken] in cache used by the SDK
     */
    fun clearCache()

    /**
     * [AccessToken] 업데이트 이벤트를 구독할 수 있는 hot [ConflatedBroadcastChannel]를 리턴한다.
     * 토큰 발급에 성공하거나 ApiClient 들을 사용하여 API 호출 중 토큰이 자동 갱신 되는 경우에 이벤트를 생성한다.
     *
     * @return [ConflatedBroadcastChannel] of [AccessToken]
     */
    @ExperimentalCoroutinesApi
    fun observe(): ConflatedBroadcastChannel<AccessToken>

    companion object {
        val instance: AccessTokenRepo by lazy {
            DefaultAccessTokenRepo()
        }
    }
}