package com.kakao.sdk.login.data

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
data class User(@SerializedName("kaccount_email") val email: String?,
                @SerializedName("kaccount_email_verified") val emailVerified: Boolean?,
                val id: Long,
                val uuid: String?,
                @SerializedName("service_user_id") val serviceUserId: Long?,
                @SerializedName("remaining_invite_count") val remainingInviteCount: Int?,
                @SerializedName("remaining_group_msg_count") val remainingGroupMsgCount: Int?,
                val properties: Map<String, String>?) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}