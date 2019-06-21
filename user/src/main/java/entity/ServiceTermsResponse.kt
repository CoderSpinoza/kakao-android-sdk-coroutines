package com.kakao.sdk.user.entity

/**
 * 3rd 의 동의항목 내역 응답 모델.
 * @property userId app user id
 * @property allowedServiceTerms 사용자가 동의한 3rd의 약관 항목들
 *
 * @author kevin.kang. Created on 04/04/2019..
 */
data class ServiceTermsResponse(val userId: Long, val allowedServiceTerms: List<ServiceTerms>?)