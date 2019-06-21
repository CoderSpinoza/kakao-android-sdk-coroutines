package com.kakao.sdk.kakaotalk.entity

/**
 * /v1/api/talk/plusfriends 응답
 *
 * @property userId 요청자의 사용자 ID
 * @property plusFriends 각 플러스친구와 요청 사용자의 관계 정보
 *
 * @since 2.0.0
 *
 * @author kevin.kang. Created on 05/04/2019..
 */
class PlusFriendsResponse(val userId: Long, val plusFriends: List<PlusFriendInfo>)