package com.kakao.sdk.network.data

import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 5. 2..
 */
open class ApiErrorResponse(@SerializedName("code") val code: Int,
                       @SerializedName("msg") open val message: String)