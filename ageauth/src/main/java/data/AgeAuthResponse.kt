package data

import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 4. 24..
 */
data class AgeAuthResponse(val id: Long,
                           @SerializedName("auth_level") val authLevel: AgeAuthLevel?,
                           @SerializedName("auth_level_code") val authLevelCode: Int?,
                           @SerializedName("bypass_age_limit") val bypassAgeLimit: Boolean?,
                           @SerializedName("authenticated_at") val authenticatedAt: String?,
                           val ci: String) {
}