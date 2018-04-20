package com.kakao.sdk.login.domain

import io.reactivex.Observable

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
interface AuthCodeRepository {
    fun authCode(): Observable<String>
}