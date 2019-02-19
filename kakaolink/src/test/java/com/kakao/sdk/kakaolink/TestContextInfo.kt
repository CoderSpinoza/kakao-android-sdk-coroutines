package com.kakao.sdk.kakaolink

import com.google.gson.JsonObject
import com.kakao.sdk.common.ContextInfo

/**
 * @author kevin.kang. Created on 20/02/2019..
 */
class TestContextInfo(override val kaHeader: String, override val signingKeyHash: String, override val extras: JsonObject) : ContextInfo