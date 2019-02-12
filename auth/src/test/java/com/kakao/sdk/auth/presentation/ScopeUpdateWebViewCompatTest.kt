package com.kakao.sdk.auth.presentation

import android.os.Build
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.M])
class ScopeUpdateWebViewCompatTest : ScopeUpdateWebViewTest() {
}