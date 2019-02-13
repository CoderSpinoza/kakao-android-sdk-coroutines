package com.kakao.sdk.network

data class ApplicationInfo(
        val clientId: String = Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY)!!,
        val approvalType: String = Utility.getMetadata(ApplicationProvider.application, Constants.META_APPROVAL_TYPE) ?: "individual",
        val androidKeyHash: String = Utility.getKeyHash(ApplicationProvider.application),
        val clientSecret: String? = Utility.getMetadata(ApplicationProvider.application, Constants.META_CLIENT_SECRET)
)