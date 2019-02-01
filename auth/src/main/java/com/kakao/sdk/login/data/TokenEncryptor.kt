package com.kakao.sdk.login.data

import com.kakao.sdk.login.data.AccessToken
import io.reactivex.Observable

/**
 * @author kevin.kang. Created on 2018. 3. 28..
 */
interface TokenEncryptor {
    fun encrypt(value: AccessToken): String
    fun decrypt(value: AccessToken): Observable<AccessToken>
}