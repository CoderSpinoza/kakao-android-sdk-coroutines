package com.kakao.sdk.login.data

import java.util.*

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
data class AccessToken(val accessToken: String,
                       val refreshToken: String,
                       val accessTokenExpiresAt: Date,
                       val refreshTokenExpiresAt: Date)