package com.kakao.sdk.message.template.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.message.template.Constants

/**
 * 메시지에서 콘텐츠 영역이나 버튼 클릭 시에 이동되는 링크 정보 오브젝트.
 *
 * @param webUrl PC버전 카카오톡에서 사용하는 웹 링크 URL
 * @param mobileWebUrl 모바일 카카오톡에서 사용하는 웹 링크 URL
 * @param androidExecParams 안드로이드 카카오톡에서 사용하는 앱 링크 URL에 사용될 파라미터.
 * @param iosExecParams iOS 카카오톡에서 사용하는 앱 링크 URL에 사용될 파라미터.
 *
 * @author kevin.kang.
 */
data class LinkObject(@SerializedName(Constants.WEB_URL) val webUrl: String? = null,
                      @SerializedName(Constants.MOBILE_WEB_URL) val mobileWebUrl: String? = null,
                      @SerializedName(Constants.ANDROID_PARAMS) val androidExecParams: String? = null,
                      @SerializedName(Constants.IOS_PARAMS) val iosExecParams: String? = null)