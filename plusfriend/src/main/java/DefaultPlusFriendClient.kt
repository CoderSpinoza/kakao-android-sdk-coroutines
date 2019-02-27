package com.kakao.sdk.plusfriend

import android.content.Context
import android.net.Uri
import com.kakao.sdk.common.ApplicationInfo
import com.kakao.sdk.common.ContextInfo
import com.kakao.sdk.common.CustomTabsCommonClient
import com.kakao.sdk.common.KakaoSdkProvider
import com.kakao.sdk.common.Constants as CommonConstants
/**
 * @suppress
 * @author kevin.kang. Created on 22/02/2019..
 */
class DefaultPlusFriendClient(
    val applicationInfo: ApplicationInfo = KakaoSdkProvider.applicationContextInfo,
    val contextInfo: ContextInfo = KakaoSdkProvider.applicationContextInfo
) : PlusFriendClient {
    override fun addFriendUrl(plusFriendId: String): Uri {
        return baseUri(appKey = applicationInfo.clientId, kaHeader = contextInfo.kaHeader)
                .path("$plusFriendId/${Constants.FRIEND}").build()
    }

    override fun chatUrl(plusFriendId: String): Uri {
        return baseUri(appKey = applicationInfo.clientId, kaHeader = contextInfo.kaHeader)
                .path("$plusFriendId/${Constants.CHAT}").build()
    }

    override fun openAddFriend(context: Context, plusFriendId: String) {
        CustomTabsCommonClient.open(context, addFriendUrl(plusFriendId))
    }

    override fun openChat(context: Context, plusFriendId: String) {
        CustomTabsCommonClient.open(context, chatUrl(plusFriendId))
    }

    private fun baseUri(appKey: String, kaHeader: String): Uri.Builder {
        return Uri.Builder().scheme(CommonConstants.SCHEME)
                .authority(KakaoSdkProvider.serverHosts.plusFriend)
                .appendQueryParameter(CommonConstants.APP_KEY, appKey)
                .appendQueryParameter(Constants.KAKAO_AGENT, kaHeader)
                .appendQueryParameter(Constants.API_VER, Constants.API_VER_10)
    }
}