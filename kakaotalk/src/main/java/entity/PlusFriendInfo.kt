package com.kakao.sdk.kakaotalk.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.kakaotalk.Constants

/**
 * 유저와 플러스 친구의 관계 정보
 *
 * @property uuid plus friend uuid
 * @property encodedId encoded plus friend id (ex. https://pf.kakao.com/${plusFriendId})
 * @property relation 플친과의 관계. 추가된 상태 / 관계 없음.
 * @property updatedAt relation 변경 시각 (현재는 ADDED 상태의 친구 추가시각만 의미)
 * RFC3339 internet date/time format
 * (yyyy-mm-dd'T'HH:mm:ss'Z', yyyy-mm-dd'T'HH:mm:ss'+'HH:mm, yyyy-mm-dd'T'HH:mm:ss'-'HH:mm 가능)
 *
 * @author kevin.kang. Created on 05/04/2019..
 */
data class PlusFriendInfo(
        @SerializedName(Constants.PLUS_FRIEND_UUID) val uuid: String,
        @SerializedName(Constants.PLUS_FRIEND_PUBLIC_ID) val encodedId: String,
        val relation: PlusFriendRelation,
        @SerializedName(Constants.UPDATED_TIME) val updatedAt: String
)
