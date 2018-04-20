package com.kakao.sdk.login.data

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 4. 3..
 */
data class MissingScopesError(val msg: String,
                              val code: Int,
                              @SerializedName("api_type") val apiType: String,
                              @SerializedName("required_scopes") val requiredScopes: List<String>,
                              @SerializedName("allowed_scopes") val allowedScopes: List<String>) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}