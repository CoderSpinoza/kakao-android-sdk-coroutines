package com.kakao.sdk.plusfriend

import com.kakao.sdk.common.ApplicationInfo

/**
 * @author kevin.kang. Created on 22/02/2019..
 */
class TestApplicationInfo(
    override val clientId: String,
    override val approvalType: String,
    override val clientSecret: String? = null
) : ApplicationInfo