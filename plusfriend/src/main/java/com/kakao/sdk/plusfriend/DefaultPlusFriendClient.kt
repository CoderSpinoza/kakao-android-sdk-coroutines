package com.kakao.sdk.plusfriend

import android.net.Uri
import com.kakao.sdk.common.ApplicationInfo
import com.kakao.sdk.common.ContextInfo
import com.kakao.sdk.common.KakaoSdkProvider

/**
 * @suppress
 * @author kevin.kang. Created on 22/02/2019..
 */
class DefaultPlusFriendClient(
    applicationInfo: ApplicationInfo = KakaoSdkProvider.applicationContextInfo,
    contextInfo: ContextInfo = KakaoSdkProvider.applicationContextInfo
) : PlusFriendClient {
    override fun addFriendUrl(plusFriendId: String): Uri {
        TODO("not implemented")
    }

    override fun chatUrl(plusFriendId: String): Uri {
        TODO("not implemented")
    }

    override fun openAddFriend(plusFriendId: String) {
        TODO("not implemented")
    }

    override fun openChat(plusFriendId: String) {
        TODO("not implemented")
    }
}