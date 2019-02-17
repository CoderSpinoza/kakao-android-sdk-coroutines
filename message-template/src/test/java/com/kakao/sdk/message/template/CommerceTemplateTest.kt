package com.kakao.sdk.message.template

import com.kakao.sdk.message.template.entity.CommerceObject
import com.kakao.sdk.message.template.entity.ContentObject
import com.kakao.sdk.message.template.entity.LinkObject
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

/**
 * @author kevin.kang. Created on 17/02/2019..
 */
class CommerceTemplateTest {
    @Test fun objectType() {
        val template = CommerceTemplate(
                ContentObject("title", "imageUrl", LinkObject()),
                CommerceObject(10000)
        )

        assertEquals(Constants.TYPE_COMMERCE, template.objectType)
        assertNotNull(template.commerce)
        assertEquals(10000, template.commerce.regularPrice)
    }
}