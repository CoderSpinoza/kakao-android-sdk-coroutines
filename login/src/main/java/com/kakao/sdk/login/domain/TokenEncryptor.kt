package com.kakao.sdk.login.domain

import com.kakao.sdk.login.entity.token.AccessToken
import io.reactivex.Observable

/**
 * @author kevin.kang. Created on 2018. 3. 28..
 */
interface TokenEncryptor {
    fun encrypt(value: AccessToken): String
    fun decrypt(value: AccessToken): Observable<AccessToken>
}