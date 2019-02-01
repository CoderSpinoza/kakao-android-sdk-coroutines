package com.kakao.sdk.auth.presentation

import android.content.Context
import com.kakao.sdk.auth.data.AccessTokenRepo
import com.kakao.sdk.network.ApplicationProvider
import com.kakao.sdk.network.Constants
import com.kakao.sdk.network.Utility
import io.reactivex.Single

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
interface AuthCodeService {
    fun requestAuthCode(context: Context,
                        clientId: String = Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY),
                        redirectUri: String = String.format("kakao%s://oauth", Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY)),
                        approvalType: String = "individual",
                        kaHeader: String = Utility.getKAHeader(ApplicationProvider.application)
    )
    fun requestAuthCode(context: Context,
                        scopes: List<String>,
                        clientId: String = Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY),
                        redirectUri: String = String.format("kakao%s://oauth", Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY)),
                        approvalType: String = "individual",
                        kaHeader: String = Utility.getKAHeader(ApplicationProvider.application)
    ): Single<String>

    companion object {
        val instance by lazy {
            DefaultAuthCodeService(AccessTokenRepo.instance.observe()) as AuthCodeService
        }
    }
}