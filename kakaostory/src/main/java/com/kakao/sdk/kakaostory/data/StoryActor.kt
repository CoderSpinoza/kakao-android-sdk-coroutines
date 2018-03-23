package com.kakao.sdk.kakaostory.data

import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
data class StoryActor(@SerializedName("display_name") val displayName: String,
                      @SerializedName("profile_thumbnail_url") val profileThumbnailUrl: String)