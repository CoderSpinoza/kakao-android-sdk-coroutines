package com.kakao.sdk.message.template

import com.kakao.sdk.common.KakaoGsonFactory
import com.kakao.sdk.message.template.entity.ContentObject
import com.kakao.sdk.message.template.entity.LinkObject
import org.junit.jupiter.api.Test

class LocationTemplateTest {
    @Test
    fun empty() {
        val template = LocationTemplate(
                address = "address",
                content = ContentObject("title", "imageUrl", LinkObject()))
        println(KakaoGsonFactory.pretty
                .toJson(template, LocationTemplate::class.java))
    }
}