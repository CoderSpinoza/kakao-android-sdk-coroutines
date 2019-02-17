package com.kakao.sdk.message.template

import com.kakao.sdk.message.template.entity.ButtonObject
import com.kakao.sdk.message.template.entity.ContentObject
import com.kakao.sdk.message.template.entity.LinkObject
import com.kakao.sdk.message.template.entity.SocialObject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FeedTemplateTest {
    @Test fun objectTypeExists() {
        val template = FeedTemplate(ContentObject("title", "imageUrl", LinkObject()))
        assertEquals(Constants.TYPE_FEED, template.objectType)
    }

    @Test fun social() {
        val template = FeedTemplate(
                content = ContentObject("title", "imageUrl", LinkObject()),
                social = SocialObject(likeCount = 1)
        )
        assertNotNull(template.social)
        assertEquals(1, template.social?.likeCount)
    }

    @Test fun addButtons() {
        val template = FeedTemplate(
                content = ContentObject("title", "imageUrl", LinkObject()),
                social = SocialObject(likeCount = 1),
                buttons = mutableListOf(
                        ButtonObject("title", LinkObject()),
                        ButtonObject("title", LinkObject())
                )
        )
        assertEquals(2, template.buttons.size)
        template.buttons.add(ButtonObject("title", LinkObject()))
        assertEquals(3, template.buttons.size)
    }
}