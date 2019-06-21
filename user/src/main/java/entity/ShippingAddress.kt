package com.kakao.sdk.user.entity

/**
 * 배송지 정보
 *
 * @property id 배송지 ID
 * @property name 배송지 이름
 * @property isDefault 기본 배송지 여부
 * @property updatedAt 수정시각의 timestamp
 * @property type 배송지 타입. 구주소(지번,번지 주소) 또는 신주소(도로명 주소). "OLD" or "NEW"
 * @property baseAddress 우편번호 검색시 채워지는 기본 주소
 * @property detailAddress 기본 주소에 추가하는 상세 주소
 * @property receiverName 수령인 이름
 * @property receiverPhoneNumber1 수령인 연락처
 * @property receiverPhoneNumber2 수령인 추가 연락처
 * @property zoneNumber 신주소 우편번호. 신주소인 경우에 반드시 존재함.
 * @property zipCode 구주소 우편번호. 우편번호를 소유하지 않는 구주소도 존재하여, 구주소인 경우도 해당값이 없을 수 있음.
 *
 * @author kevin.kang. Created on 04/04/2019..
 */
data class ShippingAddress(
        val id: Long,
        val name: String,
        val isDefault: Boolean,
        val updatedAt: Int,
        val type: String,
        val baseAddress: String,
        val detailAddress: String,
        val receiverName: String?,
        val receiverPhoneNumber1: String?,
        val receiverPhoneNumber2: String?,
        val zoneNumber: String?,
        val zipCode: String?
)