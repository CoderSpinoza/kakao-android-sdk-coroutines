# Kakao Android SDK with Coroutines

Android SDK for Kakao Open API written in coroutines. 

## Getting Started

Coroutines is officially released with Kotlin 1.3 and now becoming more and more popular.
Many developers are turning to coroutines to get rid of callback-based async code or over-spec RxJava with high learning curve.
Kakao Android SDK is re-written with Coroutines to achieve 

### Setting up the dependency

Not released yet. Pasting below code wouldn't work for now.
This SDK is modularized as below:

1. auth-coroutines
1. user-coroutines
1. talk-coroutines
1. story-coroutines
1. link-coroutines
1. plusfriend-coroutines
1. kakaonavi-coroutines

```gradle
dependencies {
    implementation 'com.kakao:sdk:user-coroutines:0.1.0'
    implementation 'com.kakao.sdk:link-coroutines:0.1.0'
    
    ...
}
```

It is recommended to use the same sdk version among modules so that there is no version conflict.

### Transitive dependencies

This SDK requires the following transitive dependencies (other than coroutines):

1. Gson
1. Retrofit (2.6.0)

Above are minimum dependencies to get rid of repetitive json parsing and boilerplate network code.

## Implementation

### Initializing SDK

You have to initialize SDK before you call any of the APIs.
Initializing SDK is as simple as below:

```kotlin
class KakaoApplication : Application() {
    override fun onCreate() {
        super.onCreate() 
        KakaoSdkProvider.applicationContextInfo =
                ApplicationContextInfo(
                        context = this,
                        clientId = "dd4e9cb75815cbdf7d87ed721a659baf")
    }
}
```

You have to provide application context at initialization so that SDK can find necessary metadata with it.
It is conventional to initialize SDK in Application#onCreate() method since it is guaranteed to be run before any part of your application code, but this is not a strict cosntraint.

### Getting access token

First, users have to get access token in order to call Kakao API. Access tokens are issued according to OAuth 2.0 spec.

1. kakao account authentication
1. user agreement (skip if not necessary)
1. authorization codem (via redirect)
1. issue access token (via POST API)

Sample login code is pasted below:

```kotlin
fun  loginButtonClicked() = launch {
    val code = AuthCodeService.instance.requestAuthCode(this@LoginActivity)
    withContext(Dispatchers.IO) {
        AuthApiClient.instance.issueAccessToken(authCode = code)
    }
    val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
    mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
            Intent.FLAG_ACTIVITY_CLEAR_TASK or
            Intent.FLAG_ACTIVITY_CLEAR_TOP
    startActivity(mainIntent)
}
```

### Calling token-based API

After ensuring that access token does exist with above step, you can call token-based API. Below are set of APIs that are currently supported by this SDK.

1. UserApiClient
1. TalkApiClient
1. StoryApiClient

Below is an example of calling _/v2/user/me_ API with _UserApi_ class.

### Other APIs

#### KakaoLink

#### KakaoNavi

#### PlusFriend

## Customizing SDK

Kakao SDK with Coroutines support customizations in many layers.
