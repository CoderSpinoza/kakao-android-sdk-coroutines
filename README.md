# V1 문제점

1. 개별 클래스가 비대하다.
    1. Session 클래스가 가장 큰 예.
    1. 유저가 하나의 클래스만 알면 되기 떄문에 편리한 장점은 있다.
    1. Single Responsibility Principle에 위배.
    1. SDK를 사용하게 되면 API와 OAuth flow를 전혀 알지 못하게 된다.
    1. 개발자 또한 유지 보수가 쉽지 않음.
1. 완벽하게 해줄 수 없는 것들을 해주고 있다.
    1. 토큰 리프레시 또는 스콮 업데이트 등을 SDK가 자동으로 처리해준다.
    각 flow들 사이에 클라 로깅을 지금 넣을래야 넣을수가 없는 구조이다.
    1. Scope update는 개인적인 생각으로는 절대 자동으로 처리해주면 안된다고 생각한다.
1. 추상적인 개념의 클래스, 인터페이스, 메소드 들이 많다.
    1. Session#hasValidAccessToken()
        1. 위 메소드의 경우 절대 시맨틱을 지킬 수 없다.
        절대 클라 쪽에서는 valid한 액세스 토큰인지 확신할 수 없기 때문.
        실제로 혼란스럽다는 의견들이 있었고 가이드하기 쉽지 않다.
1. Minor한 클라 쪽 최적화 때문에 복잡하고 불필요한 로직들이 많이 들어가게 된다.
    1. 위의 Session#hasValidAccessToken() 같은 예가 대표적.
    1. SharedPreferencesCache 같은 경우 캐싱 로직을 SharedPreferences가 가지고 있을텐데 굳이 별도로 메모리 레이어를 둘 필요가 없다.
    Data type conversion 같은 부분만 처리하면 될 듯.
1.  모바일 환경과 SDK의 특성상 테스팅이 고려되지 않은 설계이다.

# 세부 개선 사항
1. 암호화는 필수 옵션. 단방향 마이그레이션만 제공하면 된다. V1에 있는 불필요한 옵션들 다 제거.
1. Retrofit.Builder를 통하여 OkHttpClient를 override 할 수 있도록 한다.
    1. 개발자가 직접 LoggingInterceptor를 넣을 수 있도록 반드시 열어야 한다.
1. Clean Architecture 개념을 최소한으로 도입한다.

# 세부 설계

## 공통

RxJava 사용하여 펑셔널하고 테스트 용이하게 코드를 작성한다.

Uncle Bob의 Clean Architecture 를 기반으로 설계한다. 아래 두가지 방향이 있을 것 같다.

1. SDK 코드를 최소화한다.
    - SDK 코드는 엄밀히 말하면 대부분 네트워크 레이어이기 때문에 Clean Architecture 의 Data Layer 쪽만 제공한다.
    - Domain layer 의 엔티티 모델등과 Data layer 와 SDK 가 아니라 앱 쪽에서 직접 구현.
1. Domain layer 와 Presentation layer 에도 모델들과 매퍼를 제공한다.
    - 순수 코틀린으로 짜여진 데이터 모델과 Parcelable 을 구현하는 데이터 모델이 필요할 수도 있다.
    - V1 에서는 Parcelable 구현을 제공하낟.

## 네트워크

1. Retrofit 을 사용하여 네트워크 boilerplate 코드를 줄이려고 한다.
현재 Request 객체들은 상속을 기반으로 구현되어 있다.
Composition over Inheritance Principle 에 따라
1. 파라미터 개수에 따라 엄격하게 파라미터 클래스를 만든다.
1. 코틀린의 optional, default  파라미터를 사용할 수 있기 때문에 빌더 패턴은 더이상 사용하지 않는다.

## 로그인

Session 하나만 알면 됐던 V1 과는 다르게 아래의 인터페이스들을 주로 사용하게 된다.

1. AuthCodeService
1. AuthApiClient

유저가 주로 로그인 시 사용하게 되는 코드 스니펫은 아래와 같다.

```kotlin

fun onLoginButtonClick() {
    AuthCodeService.instance.requestAuthCode(this)
}

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val code = intent?.data?.getQueryParameter("code") ?: return

        AuthApiClient.instance.issueAccessToken(authCode = code)
                .subscribe { _ ->
                    val mainIntent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(mainIntent)
                }
    }


```

ApiErrorInterceptor와 UserApiClient는 shouldClose라는 Observable을 가진다.
InvalidTokenException과 /v1/user/logout, /v1/user/unlink 시 액세스토큰 클리어를 해줘야 하기 때문이다.

SDK.init() 메소드를 따로 만들지 않을 것이기 때문에 사용성에 따라 init 순서를 고려해볼 필요가 있다.
SDK.init()은 ApplicationProvider.application 세터로 대체.

### 로그인된 경우 (토큰이 있는 경우)
TalkApiClient -> ApiService -> ApiErrorInterceptor -> AccessTokenRepo


### 로그인 화면
AccessTokenRepo -> ApplicationProvider.application -> UserApiClient -> ApiService


### 테스팅

크게 로그인 쪽 모듈과 네트워크 모듈, 개별 API 모듈, 그리고 통합 테스트 정도로 나눌 수 있다.

더 간단한 API 모듈부터 먼저 얘기해보면,

1. Request test
1. Response test (Json -> Model 변환)
1. Error response test

위 테스트들은 Retrofit 과 OkHttp 라이브러리들이 내부적으로 정상 동작한다는 가정하에 실행한다.

위 테스트들은 어떻게 보면 self-declarative 한 코드들을 테스트하는 것일 수 있지만 그래도 최소한의 영역이라고 결정.

네트워크 모듈의 경우 AppKeyInterceptor 또는 KakaoAgentInterceptor 등 인터셉터 위주의 테스트를 진행한다.
KakaoConverterFactory 같은 경우는 custom converter인데 테스트 하면 좋을 것 같다.
KakaoConverterFactory 같은 경우는 내부에 안드로이드 컨텍스트 관련 디펜던시가 없기 때문에 isolation 해서 테스팅할 필요가 없다.
파라미터 중 Map -> Json으로 바꿔주는 역할인데, 그냥 각 API 사용처에서 테스트해도 될 듯 하다.
그에 반면 AppKeyInterceptor 와 KakaoAgentInterceptor 는 컨텍스트를 내부적으로 스태틱하게 참조하고 있으므로 Robolectric 테스트로 진행한다.

API 모듈과 네트워크 모듈의 플로우를 살펴 보면

1. ApiClient -> Api
    1. Client 쪽은 AccessTokenRepo, ApiErrorInterceptor 와의 관계만 테스트해보면 될 것 같다.
    1. 기본 메인 쓰레드에서 동작하는지 확인
1. Retrofit -> OkHttp
    1. OkHttp layer 에 테스팅 모듈에서 테스트 가능 (MockWebServer)
1. OkHttp -> (Server) -> OkHttp
    1. 실제 구현은 믿고 간다. Integration testing 할 때 간단히 체크
1. OkHttp -> Retrofit (Response)
    1. Json -> Model 변환 테스트 with Gson

로그인 쪽 모듈은

1. AccessTokenRepo
1.
## Questions
1. DI 시에 Observable 을 constructor injection 해도 되나?
1. 인터페이스 굳이 필요한가? 필요하다면 어느 경우에 필요한가? 인터페이스는 메소드가 최대한 적게 설계.
1. RxJava 와 Retrofit 외부 라이브러리 쓸 때 주의해야할 점?
1. 파라미터 클래스화?
1. Blocking interface가 필요할까?


AccessTokenRepo 를 업데이트 하는 Client들
- AuthApiClient
    - 토큰 업데이트
- ApiErrorInterceptor
    - InvalidTokenException 시
- UserApiClient
