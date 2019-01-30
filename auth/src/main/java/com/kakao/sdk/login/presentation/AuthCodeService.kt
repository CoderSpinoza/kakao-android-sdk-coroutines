package com.kakao.sdk.login.presentation

import android.content.Context
import com.kakao.sdk.login.domain.AccessTokenRepo
import io.reactivex.Observable
import io.reactivex.Single

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
interface AuthCodeService {
    fun requestAuthCode(context: Context): Single<String>
    fun requestAuthCode(context: Context, scopes: List<String>, approvalType: String = "individual"): Single<String>

    companion object {
        val instance by lazy {
            DefaultAuthCodeService(AccessTokenRepo.instance.observe()) as AuthCodeService
        }
    }
}