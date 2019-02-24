package com.kakao.sdk.common

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.JsonObject

/**
 * @author kevin.kang. Created on 20/02/2019..
 */
class ApplicationContextInfo(context: Context,
                             clientId: String,
                             approvalType: String = "individual",
                             clientSecret: String? = null
) : ApplicationInfo, ContextInfo {
    private val mClientId: String = clientId
    private val mApprovalType: String = approvalType
    private val mClientSecret: String? = clientSecret
    private val mKaHeader: String = Utility.getKAHeader(context)
    private val mKeyHash: String = Utility.getKeyHash(context)
    private val mExtras: JsonObject = Utility.getExtras(context)
    private val mSharedPreferences: SharedPreferences = context.getSharedPreferences(clientId, Context.MODE_PRIVATE)

    override val clientId: String
        get() = mClientId
    override val approvalType: String
        get() = mApprovalType
    override val clientSecret: String?
        get() = mClientSecret

    override val kaHeader: String
        get() = mKaHeader
    override val signingKeyHash: String
        get() = mKeyHash
    override val extras: JsonObject
        get() = mExtras

    val sharedPreferences: SharedPreferences
        get() = mSharedPreferences
}