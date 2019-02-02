package com.kakao.sdk.auth.model

import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 5. 5..
 */
data class AuthErrorResponse(@SerializedName("error") val error: String,
                        @SerializedName("errorDescription") val errorDescription: String)