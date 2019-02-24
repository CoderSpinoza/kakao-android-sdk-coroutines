package com.kakao.sdk.auth.model

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 4. 3..
 */
class MissingScopesErrorResponse(
    val code: Int,
    @SerializedName("msg") val message: String,
    val apiType: String,
    val requiredScopes: List<String>,
    val allowedScopes: List<String>
) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}