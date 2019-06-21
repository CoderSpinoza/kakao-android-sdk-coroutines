package com.kakao.sdk.partner

import com.kakao.sdk.common.ServerHosts

/**
 * @author kevin.kang. Created on 22/02/2019..
 */
class PhasedServerHosts(phase: KakaoPhase) : ServerHosts() {
    override val kauth: String =
            when (phase) {
                KakaoPhase.DEV -> "alpha-kauth.kakao.com"
                KakaoPhase.SANDBOX -> "sandbox-kauth.kakao.com"
                KakaoPhase.CBT -> "beta-kauth.kakao.com"
                KakaoPhase.PRODUCTION -> "kauth.kakao.com"
            }

    override val kapi: String =
            when (phase) {
                KakaoPhase.DEV -> "alpha-kapi.kakao.com"
                KakaoPhase.SANDBOX -> "sandbox-kapi.kakao.com"
                KakaoPhase.CBT -> "beta-kapi.kakao.com"
                KakaoPhase.PRODUCTION -> "kapi.kakao.com"
            }

    override val account: String =
            when (phase) {
                KakaoPhase.DEV -> "alpha-auth.kakao.com"
                KakaoPhase.SANDBOX -> "sandbox-auth.kakao.com"
                KakaoPhase.CBT -> "beta-auth.kakao.com"
                KakaoPhase.PRODUCTION -> "auth.kakao.com"
            }

    override val sharer: String =
            when (phase) {
                KakaoPhase.DEV -> "alpha-sharer.devel.kakao.com"
                KakaoPhase.SANDBOX -> "sandbox-sharer.devel.kakao.com"
                KakaoPhase.CBT -> "beta-sharer.kakao.com"
                KakaoPhase.PRODUCTION -> "sharer.kakao.com"
            }

    override val navi: String =
            when (phase) {
                KakaoPhase.DEV, KakaoPhase.SANDBOX -> "sandbox-kakaonavi-wguide.kakao.com"
                KakaoPhase.CBT, KakaoPhase.PRODUCTION -> "kakaonavi-wguide.kakao.com"
            }

    override val plusFriend: String =
            when (phase) {
                KakaoPhase.DEV, KakaoPhase.SANDBOX -> "sandbox-pf.kakao.com"
                KakaoPhase.CBT -> "beta-pf.kakao.com"
                KakaoPhase.PRODUCTION -> "pf.kakao.com"
            }
}