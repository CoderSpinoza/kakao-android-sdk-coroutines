package com.kakao.sdk.user.entity

import com.google.gson.GsonBuilder

/**
 * @author kevin.kang. Created on 2018. 4. 25..
 */
data class UserAccount(
        val emailNeedsAgreement: Boolean,
        val isEmailVerified: Boolean?,
        val isEmailValid: Boolean?,
        val email: String?,
        val isKakaotalkUser: Boolean?,
        val phoneNumberNeedsAgreement: Boolean?,
        val phoneNumber: String?,
        val ageRangeNeedsAgreement: Boolean?,
        val ageRange: String?,
        val birthdayNeedsAgreement: Boolean?,
        val birthday: String?,
        val birthyearNeedsAgreement: Boolean?,
        val birthyear: String?,
        val genderNeedsAgreement: Boolean?,
        val gender: String,
        val ciNeedsAgreement: Boolean?,
        val ci: String?,
        val ciAuthenticatedAt: String?
) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}