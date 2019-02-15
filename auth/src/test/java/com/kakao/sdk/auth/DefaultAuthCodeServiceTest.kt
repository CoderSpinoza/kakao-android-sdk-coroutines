package com.kakao.sdk.auth

import android.app.Activity
import android.os.Bundle
import com.kakao.sdk.auth.exception.AuthCancelException
import com.kakao.sdk.auth.exception.AuthResponseException
import com.kakao.sdk.auth.model.AccessToken
import com.kakao.sdk.auth.presentation.ScopeUpdateWebViewActivity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DefaultAuthCodeServiceTest {
    lateinit var authCodeService: DefaultAuthCodeService
    lateinit var observer: TestObserver<String>
    lateinit var bundle: Bundle

    @Before fun setup() {
        val testToken = AccessToken(accessToken = "test_access_token", refreshToken = "test_refresh_token")
        authCodeService = DefaultAuthCodeService(Observable.just(testToken))
        observer = TestObserver()
        bundle = Bundle()
    }

    @Test fun onReceivedResult() {
        val bundle = Bundle()
        bundle.putParcelable(ScopeUpdateWebViewActivity.KEY_URL, TestUriUtility.successfulRedirectUri())
        Single.create<String> {
            authCodeService.onReceivedResult(Activity.RESULT_OK, bundle, it)
        }.subscribe(observer)

        observer.assertNoErrors()
        observer.assertValue {
            it == "authorization_code"
        }
        observer.assertComplete()
    }

    @Test fun onReceivedResultWithFailure() {
        val bundle = Bundle()
        bundle.putParcelable(ScopeUpdateWebViewActivity.KEY_URL, TestUriUtility.failedRedirectUri())
        Single.create<String> {
            authCodeService.onReceivedResult(Activity.RESULT_OK, bundle, it)
        }.subscribe(observer)
        observer.assertError {
            it.javaClass == AuthResponseException::class.java
            val casted = it as AuthResponseException
            casted.response.error == "invalid_grant"
            casted.response.errorDescription == "error_description"
        }
    }

    @Test fun onReceivedResultWithCancel() {
        val bundle = Bundle()
        bundle.putSerializable(ScopeUpdateWebViewActivity.KEY_EXCEPTION, AuthCancelException())
        Single.create<String> {
            authCodeService.onReceivedResult(Activity.RESULT_CANCELED, bundle, it)
        }.subscribe(observer)
        observer.assertError {
            it.javaClass == AuthCancelException::class.java
        }
    }
}