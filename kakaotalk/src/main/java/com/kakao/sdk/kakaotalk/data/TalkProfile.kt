package com.kakao.sdk.kakaotalk.data

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.kakaotalk.Constants

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
data class TalkProfile(@SerializedName(Constants.NICKNAME) val nickname: String,
                       @SerializedName(Constants.PROFILE_IMAGE_URL) val profileImageUrl: String,
                       @SerializedName(Constants.THUMBNAIL_URL) val thumbnailUrl: String,
                       @SerializedName(Constants.COUNTRY_ISO) val countryISO: String)