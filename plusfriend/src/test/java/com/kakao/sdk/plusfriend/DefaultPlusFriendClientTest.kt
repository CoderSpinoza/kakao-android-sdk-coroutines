package com.kakao.sdk.plusfriend

import com.google.gson.JsonObject
import com.kakao.sdk.common.Constants
import org.junit.Before
import org.junit.Ignore
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

    @Before fun setup() {
        client = DefaultPlusFriendClient(
                applicationInfo = TestApplicationInfo("client_id", "individual"),
                contextInfo = TestContextInfo("ka_header", "key_hash", JsonObject())
        )
    }

    @Ignore
    @Test fun addFriendUrl() {
        val uri = client.addFriendUrl("friend_id")

        assertEquals(Constants.SCHEME, uri.scheme)
    }

    @Test fun chatUrl() {
    }
}