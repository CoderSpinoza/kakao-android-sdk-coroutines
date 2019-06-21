package com.kakao.sdk.auth

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 4. 24..
 */
object Constants {
    const val V2_ME_PATH = "/v2/user/me"
    const val V1_ACCESS_TOKEN_INFO_PATH = "/v1/user/access_token_info"
    const val V1_UPDATE_PROFILE_PATH = "/v1/user/update_profile"
    const val V1_AGE_AUTH_INFO_PATH = "/v1/user/age_auth"
    const val V1_LOGOUT_PATH = "/v1/user/logout"
    const val V1_UNLINK_PATH = "/v1/user/unlink"

    const val SECURE_RESOURCE = "secure_resource"
    const val PROPERTIES = "properties"
    const val PROPERTY_KEYS = "property_keys"
    const val PROPERTYKEYS = "propertyKeys"
    const val AGE_LIMIT = "age_limit"

    const val AUTHORIZE_PATH = "oauth/authorize"
    const val TOKEN_PATH = "oauth/token"

    const val CLIENT_ID = "client_id"
    const val REDIRECT_URI = "redirect_uri"
    const val APPROVAL_TYPE = "approval_type"
    const val ANDROID_KEY_HASH = "android_key_hash"
    const val CODE = "code"
    const val ERROR = "error"
    const val ERROR_DESCRIPTION = "error_description"
    const val REFRESH_TOKEN = "refresh_token"
    const val CLIENT_SECRET = "client_secret"
    const val GRANT_TYPE = "grant_type"
    const val RESPONSE_TYPE = "response_type"
    const val SCOPE = "scope"
    const val RT = "RT"

    const val INDIVIDUAL = "individual"
    const val AUTHORIZATION_CODE = "authorization_code"

    const val ID = "id"
    const val HAS_SIGNED_UP = "has_signed_up"
    const val KAKAO_ACCOUNT = "kakao_account"
    const val FOR_PARTNER = "for_partner"

    const val EMAIL_NEEDS_AGREEMENT = "email_needs_agreement"
    const val IS_EMAIL_VERIFIED = "is_email_verified"
    const val EMAIL = "email"
    const val IS_KAKAOTALK_USER = "is_kakaotalk_user"
    const val HAS_PHONE_NUMBER = "has_phone_number"
    const val PHONE_NUMBER_NEEDS_AGREEMENT = "phone_number_needs_agreement"
    const val PHONE_NUMBER = "phone_number"
    const val DISPLAY_ID = "display_id"

    const val APPID = "appId"
    const val EXPIRESINMILLIS = "expiresInMillis"
    const val KACCOUNT_ID = "kaccount_id"

    const val ACCESS_TOKEN = "access_token"
    const val EXPIRES_IN = "expires_in"
    const val REFRESH_TOKEN_EXPIRES_IN = "refresh_token_expires_in"
    const val TOKEN_TYPE = "token_type"

    //
    const val KEY_URL = "key.url"
    const val KEY_LOGIN_INTENT = "key.login.intent"
    const val KEY_REQUEST_CODE = "key.request.code"
    const val KEY_REDIRECT_URI = "key.redirect_uri"
    const val KEY_HEADERS = "key.extra.headers"
    const val KEY_EXCEPTION = "key.exception"
    const val KEY_RESULT_RECEIVER = "key.result.receiver"

    val EXTRA_APPLICATION_KEY = "com.kakao.sdk.talk.appKey"
    val EXTRA_REDIRECT_URI = "com.kakao.sdk.talk.redirectUri"
    val EXTRA_KA_HEADER = "com.kakao.sdk.talk.kaHeader"
    val EXTRA_EXTRAPARAMS = "com.kakao.sdk.talk.extraparams"

    val EXTRA_REDIRECT_URL = "com.kakao.sdk.talk.redirectUrl"
    val EXTRA_ERROR_DESCRIPTION = "com.kakao.sdk.talk.error.description"
    val EXTRA_ERROR_TYPE = "com.kakao.sdk.talk.error.type"

    val NOT_SUPPORT_ERROR = "NotSupportError" // KakaoTalk installed but not signed up
}