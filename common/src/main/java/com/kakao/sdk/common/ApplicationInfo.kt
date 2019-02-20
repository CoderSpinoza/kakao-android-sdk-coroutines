package com.kakao.sdk.common

/**
 * 카카오 API 플랫폼 앱 설정
 *
 * @author kevin.kang.
 */
interface ApplicationInfo {
    val clientId: String
    val approvalType: String
    val clientSecret: String?
}