package com.kakao.sdk.message.template.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.message.template.Constants

/**
 * 콘텐츠의 내용을 담고 있는 오브젝트.
 *
 * @param title 콘텐츠의 타이틀
 * @param imageUrl 콘텐츠의 이미지 URL
 * @param link 콘텐츠 클릭 시 이동할 링크 정보
 * @param imageWidth 콘텐츠의 이미지 너비 (단위: 픽셀)
 * @param imageHeight 콘텐츠의 이미지 높이 (단위: 픽셀)
 *
 * @author kevin.kang.
 */
data class ContentObject(@SerializedName(Constants.TITLE) val title: String,
                         @SerializedName(Constants.IMAGE_URL) val imageUrl: String,
                         @SerializedName(Constants.LINK) val link: LinkObject,
                         @SerializedName(Constants.IMAGE_WIDTH) val imageWidth: Int? = null,
                         @SerializedName(Constants.IMAGE_HEIGHT) val imageHeight: Int? = null)