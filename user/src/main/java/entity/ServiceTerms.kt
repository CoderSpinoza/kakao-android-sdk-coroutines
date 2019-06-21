package com.kakao.sdk.user.entity

/**
 * 유저의 개별 동의항목 동의 내역.
 *
 * @property tag 동의한 약관의 tag. 3rd 에서 설정한 값
 * @property agreedAt 동의한 시간. 약관이 여러번 뜨는 구조라면, 마지막으로 동의한 시간. RFC3339 internet date/time format (yyyy-mm-dd'T'HH:mm:ss'Z', yyyy-mm-dd'T'HH:mm:ss'+'HH:mm, yyyy-mm-dd'T'HH:mm:ss'-'HH:mm 가능)
 *
 * @author kevin.kang. Created on 04/04/2019..
 */
class ServiceTerms(val tag: String, val agreedAt: String)