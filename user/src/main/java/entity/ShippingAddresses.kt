package com.kakao.sdk.user.entity

/**
 * 배송지 API 응답.
 *
 * @property userId 배송지 정보를 요청한 사용자 아이디
 * @property hasShippingAddresses
 * @property shippingAddressesNeedsAgreement 배송지 정보 조회를 위하여 유저에게 제3자 정보제공동의를 받아야 하는지 여부
 * @property shippingAddresses 사용자의 배송지 정보 리스트. 최신 수정순 (단, 기본 배송지는 수정시각과 상관없이 첫번째에 위치)
 *
 * @author kevin.kang. Created on 04/04/2019..
 */
data class ShippingAddresses(
    val userId: Long,
    val hasShippingAddresses: Boolean,
    val shippingAddressesNeedsAgreement: Boolean,
    val shippingAddresses: List<ShippingAddress>
)