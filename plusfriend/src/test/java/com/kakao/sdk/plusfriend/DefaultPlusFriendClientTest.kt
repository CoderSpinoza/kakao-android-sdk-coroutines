package com.kakao.sdk.plusfriend

import android.net.Uri
import com.kakao.sdk.common.Constants as CommonConstants

import com.google.gson.JsonObject
import com.kakao.sdk.common.ApplicationInfo
import com.kakao.sdk.common.ContextInfo
import com.kakao.sdk.common.KakaoSdkProvider
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * @author kevin.kang. Created on 22/02/2019..
 */
@RunWith(RobolectricTestRunner::class)
class DefaultPlusFriendClientTest {
    private lateinit var client: PlusFriendClient
    private lateinit var applicationInfo: ApplicationInfo
    private lateinit var contextInfo: ContextInfo

    @Before fun setup() {
        applicationInfo = TestApplicationInfo("client_id", "individual")
        contextInfo = TestContextInfo("ka_header", "key_hash", JsonObject())
        client = DefaultPlusFriendClient(applicationInfo, contextInfo)
    }

    @Test fun addFriendUrl() {
        val uri = client.addFriendUrl("friend_id")
        assertEquals("/friend_id/friend", uri.path)
        assertCommon(uri)
    }

    @Test fun chatUrl() {
        val uri = client.chatUrl("friend_id")
        assertEquals("/friend_id/chat", uri.path)
        assertCommon(uri)
    }

    private fun assertCommon(uri: Uri) {
        assertEquals(CommonConstants.SCHEME, uri.scheme)
        assertEquals(KakaoSdkProvider.serverHosts.plusFriend, uri.host)
        assertEquals(applicationInfo.clientId, uri.getQueryParameter(CommonConstants.APP_KEY))
        assertEquals("ka_header", uri.getQueryParameter(Constants.KAKAO_AGENT))
        assertEquals(Constants.API_VER_10, uri.getQueryParameter(Constants.API_VER))
    }
}