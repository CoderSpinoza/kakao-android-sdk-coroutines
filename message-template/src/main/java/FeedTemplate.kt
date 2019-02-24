package com.kakao.sdk.message.template

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.kakao.sdk.message.template.entity.ButtonObject
import com.kakao.sdk.message.template.entity.ContentObject
import com.kakao.sdk.message.template.entity.SocialObject

/**
 * 피드 기본 템플릿.
 *
 * Feed default template.
 *
 * @author kevin.kang. Created on 2018. 3. 22..
 */
open class FeedTemplate(
    @SerializedName(Constants.CONTENT) open val content: ContentObject,
    @SerializedName(Constants.SOCIAL) open val social: SocialObject? = null,
    @SerializedName(Constants.BUTTONS) open val buttons: MutableList<ButtonObject> = mutableListOf()
) : DefaultTemplate {
    @SerializedName(Constants.OBJECT_TYPE) open val objectType = Constants.TYPE_FEED
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}