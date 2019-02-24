package com.kakao.sdk.message.template.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.message.template.Constants

/**
 * 메시지 하단에 추가되는 버튼 오브젝트.
 *
 * @param title 버튼의 타이틀
 * @param link 버튼 클릭 시 이동할 링크 정보
 *
 * @author kevin.kang.
 */
data class ButtonObject(
    @SerializedName(Constants.TITLE) val title: String,
    @SerializedName(Constants.LINK) val link: LinkObject
)