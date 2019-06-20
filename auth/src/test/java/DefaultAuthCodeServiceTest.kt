package com.kakao.sdk.auth

import android.app.Activity
import android.os.Bundle
import com.kakao.sdk.auth.exception.AuthCancelException
import com.kakao.sdk.auth.exception.AuthResponseException
import com.kakao.sdk.auth.model.AccessToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.fail

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.coroutines.suspendCoroutine

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class DefaultAuthCodeServiceTest {
    lateinit var authCodeService: DefaultAuthCodeService

    @Before
    fun setup() {
        val testToken = AccessToken(
                accessToken = "test_access_token",
                refreshToken = "test_refresh_token")
        authCodeService = DefaultAuthCodeService(ConflatedBroadcastChannel(testToken))
    }

    @Test
    fun onReceivedResult() = runBlocking {
        val bundle = Bundle()
        bundle.putParcelable(
                Constants.KEY_URL,
                TestUriUtility.successfulRedirectUri())

        val code = suspendCoroutine<String> {
            authCodeService.onReceivedResult(Activity.RESULT_OK, bundle, it)
        }
        assertEquals("authorization_code", code)
    }

    @Test
    fun onReceivedResultWithFailure() {
        val bundle = Bundle()
        bundle.putParcelable(Constants.KEY_URL, TestUriUtility.failedRedirectUri())

        assertThrows(AuthResponseException::class.java) {
            runBlocking {
                val code = suspendCoroutine<String> {
                    authCodeService.onReceivedResult(Activity.RESULT_OK, bundle, it)
                }
                fail<Unit>("Exception should have been thrown")
            }
        }
    }

    @Test
    fun onReceivedResultWithCancel() {
        val bundle = Bundle()
        bundle.putSerializable(Constants.KEY_EXCEPTION, AuthCancelException())
        assertThrows(AuthCancelException::class.java) {
            runBlocking {
                val code = suspendCoroutine<String> {
                    authCodeService.onReceivedResult(Activity.RESULT_CANCELED, bundle, it)
                }
                fail<Unit>("Exception should have been thrown")
            }
        }
    }
}