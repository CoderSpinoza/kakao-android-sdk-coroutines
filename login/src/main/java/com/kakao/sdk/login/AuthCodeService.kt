package com.kakao.sdk.login

import android.content.Context
import com.kakao.sdk.login.domain.AccessTokenRepo
import io.reactivex.Observable

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
interface AuthCodeService {
    fun requestAuthCode(context: Context): Observable<String>
    fun requestAuthCode(context: Context, scopes: List<String>, approvalType: String): Observable<String>

    fun updateScopesObservable(context: Context, observable: Observable<Throwable>): Observable<Any>

    companion object {
        val instance = DefaultAuthCodeService(AccessTokenRepo.instance)
    }
}